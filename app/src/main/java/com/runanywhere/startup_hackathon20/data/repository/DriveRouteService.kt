package com.runanywhere.startup_hackathon20.data.repository

import android.content.Context
import android.util.Log
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveRequest
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.runanywhere.startup_hackathon20.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import kotlin.collections.find

/**
 * Service to fetch Learning Routes from Google Drive
 */
class DriveRouteService(private val context: Context) {

    private val driveService: Drive
    private val gson = Gson()

    companion object {
        private const val TAG = "DriveRouteService"
        private const val API_KEY = "AIzaSyCztwFMxHV3fP1Zd4j1XhpyZF6DkgCIK30"
        const val DRIVE_FOLDER_ID = "1sFgl7wGTuAGWRsQi-sNYW4Me-VrKmD9J"
    }

    init {
        // Initialize Drive service with API key for public folder access
        driveService = Drive.Builder(
            NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            { request ->
                // Add API key to all requests
                (request as? DriveRequest<*>)?.key = API_KEY
            }
        )
            .setApplicationName("EduVenture RPG")
            .build()

        Log.d(TAG, "DriveRouteService initialized with API key")
    }

    /**
     * Fetch all learning routes from the Google Drive folder
     */
    suspend fun fetchLearningRoutesFromDrive(): List<LearningRoute> = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Fetching learning routes from Drive folder: $DRIVE_FOLDER_ID")

            // List all JSON files in the folder
            val result = driveService.files().list()
                .setQ("'$DRIVE_FOLDER_ID' in parents and (mimeType='application/json' or name contains '.json') and trashed=false")
                .setFields("files(id, name, mimeType)")
                .setKey(API_KEY)
                .execute()

            val files = result.files
            Log.d(TAG, "Found ${files?.size ?: 0} files in Drive folder")

            if (files.isNullOrEmpty()) {
                Log.w(TAG, "No JSON files found in the Drive folder")
                return@withContext listOf(createFallbackMathRoute())
            }

            val routes = mutableListOf<LearningRoute>()

            // Download and parse each JSON file
            for (file in files) {
                try {
                    Log.d(TAG, "Processing file: ${file.name} (${file.id})")
                    val content = downloadFileContent(file.id)
                    val route = parseRouteFromJson(content)
                    if (route != null) {
                        routes.add(route)
                        Log.d(TAG, "Successfully parsed route: ${route.routeName}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error processing file ${file.name}: ${e.message}", e)
                }
            }

            if (routes.isEmpty()) {
                Log.w(TAG, "No valid routes parsed, using fallback")
                return@withContext listOf(createFallbackMathRoute())
            }

            Log.d(TAG, "Successfully fetched ${routes.size} learning routes from Drive")
            routes

        } catch (e: Exception) {
            Log.e(TAG, "Error fetching routes from Drive: ${e.message}", e)
            Log.e(TAG, "Falling back to default route")
            // Return fallback route if Drive fetch fails
            listOf(createFallbackMathRoute())
        }
    }

    /**
     * Download file content from Google Drive
     */
    private fun downloadFileContent(fileId: String): String {
        val outputStream = ByteArrayOutputStream()
        driveService.files().get(fileId)
            .setKey(API_KEY)
            .executeMediaAndDownloadTo(outputStream)
        return outputStream.toString("UTF-8")
    }

    /**
     * Parse a LearningRoute from JSON content
     */
    private fun parseRouteFromJson(jsonContent: String): LearningRoute? {
        return try {
            val jsonElement = JsonParser.parseString(jsonContent)
            val jsonObject = jsonElement.asJsonObject

            // Parse route metadata
            val id = jsonObject.get("id")?.asString ?: return null
            val subject = jsonObject.get("subject")?.asString ?: "Unknown"
            val routeName = jsonObject.get("routeName")?.asString ?: "Unknown Route"
            val description = jsonObject.get("description")?.asString ?: ""
            val finalBoss = jsonObject.get("finalBoss")?.asString ?: "Final Boss"
            val themeString = jsonObject.get("backgroundTheme")?.asString ?: "MYSTIC"
            val backgroundTheme = try {
                RouteTheme.valueOf(themeString.uppercase())
            } catch (e: Exception) {
                RouteTheme.MYSTIC
            }

            // Parse modules array
            val modulesArray = jsonObject.getAsJsonArray("modules")
            val modules = mutableListOf<QuestModule>()

            modulesArray?.forEach { moduleElement ->
                val moduleObj = moduleElement.asJsonObject
                val module = QuestModule(
                    id = moduleObj.get("id")?.asString ?: "",
                    moduleNumber = moduleObj.get("moduleNumber")?.asInt ?: 0,
                    title = moduleObj.get("title")?.asString ?: "",
                    topic = moduleObj.get("topic")?.asString ?: "",
                    enemyName = moduleObj.get("enemyName")?.asString ?: "",
                    enemyDescription = moduleObj.get("enemyDescription")?.asString ?: "",
                    enemyLevel = moduleObj.get("enemyLevel")?.asInt ?: 1,
                    xpReward = moduleObj.get("xpReward")?.asInt ?: 50,
                    videoUrl = moduleObj.get("videoUrl")?.asString ?: "",
                    videoStartTime = moduleObj.get("videoStartTime")?.asInt ?: 0,
                    videoEndTime = moduleObj.get("videoEndTime")?.asInt,
                    isCompleted = false,
                    isBoss = moduleObj.get("isBoss")?.asBoolean ?: false
                )
                modules.add(module)
            }

            LearningRoute(
                id = id,
                subject = subject,
                routeName = routeName,
                description = description,
                backgroundTheme = backgroundTheme,
                finalBoss = finalBoss,
                modules = modules,
                isUnlocked = true
            )

        } catch (e: Exception) {
            Log.e(TAG, "Error parsing route JSON: ${e.message}", e)
            null
        }
    }

    /**
     * Create a fallback math route (same as the original hardcoded one)
     */
    private fun createFallbackMathRoute(): LearningRoute {
        return LearningRoute(
            id = "math_route",
            subject = "Mathematics",
            routeName = "The Path of Numbers",
            description = "Journey through the mystical realm of mathematics and defeat the dark forces that threaten the kingdom!",
            backgroundTheme = RouteTheme.MYSTIC,
            finalBoss = "The Demon Lord of Mathematics",
            modules = listOf(
                QuestModule(
                    id = "math_module_1",
                    moduleNumber = 1,
                    title = "Module 1: Sets",
                    topic = "Sets - The Foundation",
                    enemyName = "The Necromancer of Sets",
                    enemyDescription = "The Necromancer of Sets has been raiding the village, capturing townsfolk in his mysterious collections. Complete this module to save the villagers and learn the ancient art of Sets!",
                    enemyLevel = 5,
                    xpReward = 50,
                    videoUrl = "https://www.youtube.com/embed/jKUpw3TyjHI",
                    videoStartTime = 0,
                    videoEndTime = 1008
                ),
                QuestModule(
                    id = "math_module_2",
                    moduleNumber = 2,
                    title = "Module 2: Relations and Functions",
                    topic = "Relations and Functions",
                    enemyName = "The Sorceress of Relations",
                    enemyDescription = "The Sorceress has cursed the land, breaking all connections between the kingdoms. Master Relations and Functions to restore harmony and defeat her dark magic!",
                    enemyLevel = 10,
                    xpReward = 75,
                    videoUrl = "PASTE_YOUTUBE_URL_HERE_FOR_RELATIONS",
                    videoStartTime = 0,
                    videoEndTime = null
                ),
                QuestModule(
                    id = "math_module_3",
                    moduleNumber = 3,
                    title = "Module 3: Trigonometric Functions",
                    topic = "Trigonometric Functions",
                    enemyName = "The Triangle Titan",
                    enemyDescription = "A colossal Titan made of triangles terrorizes the mountain pass. Learn the secrets of Trigonometric Functions to measure your way to victory!",
                    enemyLevel = 15,
                    xpReward = 100,
                    videoUrl = "PASTE_YOUTUBE_URL_HERE_FOR_TRIGONOMETRY",
                    videoStartTime = 0,
                    videoEndTime = null
                ),
                QuestModule(
                    id = "math_module_4",
                    moduleNumber = 4,
                    title = "Module 4: Complex Numbers",
                    topic = "Complex Numbers",
                    enemyName = "The Phantom of Imaginary Realm",
                    enemyDescription = "A ghostly Phantom dwells in the realm between real and imaginary. Unravel the mysteries of Complex Numbers to banish this spectral menace!",
                    enemyLevel = 20,
                    xpReward = 125,
                    videoUrl = "PASTE_YOUTUBE_URL_HERE_FOR_COMPLEX_NUMBERS",
                    videoStartTime = 0,
                    videoEndTime = null
                ),
                QuestModule(
                    id = "math_module_5",
                    moduleNumber = 5,
                    title = "Module 5: Quadratic Functions",
                    topic = "Quadratic Functions",
                    enemyName = "The Parabola Dragon",
                    enemyDescription = "A mighty Dragon whose flight path curves through the sky. Master Quadratic Functions to predict its movements and strike it down!",
                    enemyLevel = 25,
                    xpReward = 150,
                    videoUrl = "PASTE_YOUTUBE_URL_HERE_FOR_QUADRATIC",
                    videoStartTime = 0,
                    videoEndTime = null
                ),
                QuestModule(
                    id = "math_module_6",
                    moduleNumber = 6,
                    title = "Module 6: Linear Inequalities",
                    topic = "Linear Inequalities",
                    enemyName = "The Demon Lord of Mathematics",
                    enemyDescription = "The ultimate evil has risen! The Demon Lord of Mathematics threatens to plunge the world into eternal darkness. Complete this final module and master Linear Inequalities to bring back light to the world!",
                    enemyLevel = 30,
                    xpReward = 200,
                    videoUrl = "PASTE_YOUTUBE_URL_HERE_FOR_LINEAR_INEQUALITIES",
                    videoStartTime = 0,
                    videoEndTime = null,
                    isBoss = true
                )
            )
        )
    }

    /**
     * Fetch a specific route by ID from Drive
     */
    suspend fun fetchRouteById(routeId: String): LearningRoute? = withContext(Dispatchers.IO) {
        try {
            val routes = fetchLearningRoutesFromDrive()
            routes.find { it.id == routeId }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching route by ID: ${e.message}", e)
            null
        }
    }

    /**
     * Check if Drive is accessible
     */
    suspend fun isDriveAccessible(): Boolean = withContext(Dispatchers.IO) {
        try {
            driveService.files().list()
                .setQ("'$DRIVE_FOLDER_ID' in parents")
                .setFields("files(id)")
                .setPageSize(1)
                .setKey(API_KEY)
                .execute()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Drive not accessible: ${e.message}")
            false
        }
    }
}
