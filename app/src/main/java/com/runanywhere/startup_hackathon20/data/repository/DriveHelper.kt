package com.runanywhere.startup_hackathon20.data.repository

import android.content.Context
import android.util.Log
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DriveHelper(private val context: Context) {

    private var driveService: Drive? = null
    private val pdfCache = mutableMapOf<String, String>() // fileId to extracted text cache

    init {
        // Initialize PDFBox
        PDFBoxResourceLoader.init(context)
        // Initialize with unauthenticated access for public folders
        initializeUnauthenticatedDrive()
    }

    /**
     * Initialize Google Drive service without authentication for public folders
     */
    private fun initializeUnauthenticatedDrive() {
        try {
            driveService = Drive.Builder(
                NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                null // No credentials for public access
            )
                .setApplicationName("EduVenture RPG")
                .build()
            Log.d("DriveHelper", "Drive service initialized for public access")
        } catch (e: Exception) {
            Log.e("DriveHelper", "Error initializing Drive: ${e.message}")
        }
    }

    /**
     * Initialize Google Drive service with user credentials (for private folders)
     */
    fun initializeDrive(accountName: String) {
        val credential = GoogleAccountCredential.usingOAuth2(
            context,
            listOf(DriveScopes.DRIVE_READONLY)
        ).apply {
            selectedAccountName = accountName
        }

        driveService = Drive.Builder(
            NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        )
            .setApplicationName("EduVenture RPG")
            .build()

        Log.d("DriveHelper", "Drive service initialized with account: $accountName")
    }

    /**
     * Fetch list of PDF files from a specific Google Drive folder
     * Works with both public and authenticated folders
     */
    suspend fun fetchPDFsFromFolder(folderId: String): List<DriveFile> =
        withContext(Dispatchers.IO) {
            try {
                Log.d("DriveHelper", "Fetching PDFs from folder: $folderId")

                val result = driveService?.files()?.list()
                    ?.setQ("'$folderId' in parents and mimeType='application/pdf' and trashed=false")
                    ?.setFields("files(id, name, modifiedTime, size, webContentLink)")
                    ?.setOrderBy("modifiedTime desc")
                    ?.execute()

                val files = result?.files?.map { file ->
                    Log.d("DriveHelper", "Found PDF: ${file.name} (${file.id})")
                    DriveFile(
                        id = file.id,
                        name = file.name,
                        modifiedTime = file.modifiedTime?.value ?: 0,
                        size = file.getSize() ?: 0,
                        webLink = file.webContentLink
                    )
                } ?: emptyList()

                Log.d("DriveHelper", "Total PDFs found: ${files.size}")
                files
            } catch (e: Exception) {
                Log.e("DriveHelper", "Error fetching PDFs: ${e.message}", e)
                Log.e("DriveHelper", "Folder ID: $folderId")
                Log.e("DriveHelper", "Make sure the folder is publicly accessible")
                emptyList()
            }
        }

    /**
     * Download and extract text from a PDF file
     */
    suspend fun extractTextFromPDF(fileId: String, webLink: String? = null): String =
        withContext(Dispatchers.IO) {
        // Check cache first
        pdfCache[fileId]?.let {
            Log.d("DriveHelper", "Returning cached PDF content for $fileId")
            return@withContext it
        }

        try {
            Log.d("DriveHelper", "Downloading PDF: $fileId")

            // Download PDF file
            val outputStream = ByteArrayOutputStream()

            try {
                // Try authenticated download first
                driveService?.files()?.get(fileId)?.executeMediaAndDownloadTo(outputStream)
            } catch (e: Exception) {
                Log.w("DriveHelper", "Authenticated download failed, trying web link")
                // If authenticated fails and we have a web link, try direct download
                if (!webLink.isNullOrEmpty()) {
                    val directLink = webLink.replace("/view?", "/uc?export=download&")
                    val connection = URL(directLink).openConnection()
                    connection.getInputStream().use { input ->
                        input.copyTo(outputStream)
                    }
                } else {
                    throw e
                }
            }

            // Save to temporary file
            val tempFile = File.createTempFile("pdf_", ".pdf", context.cacheDir)
            FileOutputStream(tempFile).use { fos ->
                fos.write(outputStream.toByteArray())
            }

            Log.d("DriveHelper", "Extracting text from PDF")
            // Extract text using PDFBox
            val document = PDDocument.load(tempFile)
            val stripper = PDFTextStripper()
            val text = stripper.getText(document)
            document.close()

            // Clean up temp file
            tempFile.delete()

            // Cache the result
            pdfCache[fileId] = text

            Log.d("DriveHelper", "Successfully extracted ${text.length} characters from PDF")
            text
        } catch (e: Exception) {
            Log.e("DriveHelper", "Error extracting PDF text: ${e.message}", e)
            "Error: Could not extract text from PDF. ${e.message}\n\nPlease ensure:\n1. The folder is publicly accessible\n2. PDFs have 'Anyone with the link' view permission\n3. You're connected to the internet"
        }
    }

    /**
     * Summarize PDF content into chunks for better processing
     */
    fun summarizeTextForChat(text: String, maxLength: Int = 3000): String {
        return if (text.length <= maxLength) {
            text
        } else {
            // Take key sections from beginning, middle, and end
            val chunkSize = maxLength / 3
            val beginning = text.take(chunkSize)
            val middle =
                text.substring(text.length / 2 - chunkSize / 2, text.length / 2 + chunkSize / 2)
            val end = text.takeLast(chunkSize)

            """
            [Document Summary - Beginning]
            $beginning
            
            [Document Summary - Middle Section]
            $middle
            
            [Document Summary - Ending]
            $end
            """.trimIndent()
        }
    }

    /**
     * Clear PDF cache
     */
    fun clearCache() {
        pdfCache.clear()
    }
}

data class DriveFile(
    val id: String,
    val name: String,
    val modifiedTime: Long,
    val size: Long,
    val webLink: String? = null
)
