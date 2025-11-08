package com.runanywhere.startup_hackathon20.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.runanywhere.startup_hackathon20.viewmodel.ChatMessage
import com.runanywhere.startup_hackathon20.viewmodel.EduVentureViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SirRemiChatScreen(
    viewModel: EduVentureViewModel,
    onBack: () -> Unit
) {
    val chatMessages by viewModel.chatMessages.collectAsState()
    val isLoading by viewModel.isAIChatLoading.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val currentModelId by viewModel.currentModelId.collectAsState()
    val availableModels by viewModel.availableModels.collectAsState()
    val downloadProgress by viewModel.downloadProgress.collectAsState()
    val availablePDFs by viewModel.availablePDFs.collectAsState()
    val selectedPDF by viewModel.selectedPDF.collectAsState()

    var inputText by remember { mutableStateOf("") }
    var showModelSelector by remember { mutableStateOf(false) }
    var showPDFSelector by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "🤖 Sir Remi",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "Your Study Buddy",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                    }
                },
                actions = {
                    // PDF Selector Button
                    IconButton(onClick = { showPDFSelector = !showPDFSelector }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "PDFs",
                            tint = Color.White
                        )
                    }
                    // Model Selector Button
                    TextButton(onClick = { showModelSelector = !showModelSelector }) {
                        Text("AI Model", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2C1810)
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A0F0A),
                            Color(0xFF2C1810),
                            Color(0xFF3D2417)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Model Selector (collapsible)
                if (showModelSelector) {
                    ModelSelectorPanel(
                        availableModels = availableModels,
                        currentModelId = currentModelId,
                        downloadProgress = downloadProgress,
                        onDownload = { modelId -> viewModel.downloadModel(modelId) },
                        onLoad = { modelId -> viewModel.loadModel(modelId) },
                        onRefresh = { viewModel.refreshModels() }
                    )
                }

                // PDF Selector (collapsible)
                if (showPDFSelector) {
                    PDFSelectorPanel(
                        availablePDFs = availablePDFs,
                        selectedPDF = selectedPDF,
                        onSelectPDF = { pdf -> viewModel.selectPDF(pdf) },
                        onSummarizePDF = { viewModel.summarizePDF() },
                        onSetFolderId = { folderId -> viewModel.setDriveFolderId(folderId) },
                        onRefresh = { viewModel.fetchPDFsFromDrive() }
                    )
                }

                // Status message
                if (statusMessage.isNotEmpty()) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFF4A2F1F)
                    ) {
                        Text(
                            statusMessage,
                            modifier = Modifier.padding(8.dp),
                            fontSize = 12.sp,
                            color = Color(0xFFFFD700)
                        )
                    }
                }

                // Messages List
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (chatMessages.isEmpty()) {
                        item {
                            SirRemiEmptyState()
                        }
                    }

                    items(chatMessages) { message ->
                        SirRemiChatBubble(message)
                    }
                }

                // Auto-scroll to bottom
                LaunchedEffect(chatMessages.size) {
                    if (chatMessages.isNotEmpty()) {
                        listState.animateScrollToItem(chatMessages.size - 1)
                    }
                }

                // Input Field
                Surface(
                    color = Color(0xFF4A2F1F),
                    shadowElevation = 8.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            modifier = Modifier.weight(1f),
                            placeholder = {
                                Text(
                                    "Ask Sir Remi for guidance...",
                                    color = Color(0xFF8B7355)
                                )
                            },
                            enabled = !isLoading && currentModelId != null,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFF2C1810),
                                unfocusedContainerColor = Color(0xFF2C1810),
                                disabledContainerColor = Color(0xFF2C1810),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = Color(0xFFFFD700),
                                focusedBorderColor = Color(0xFFFFD700),
                                unfocusedBorderColor = Color(0xFF8B7355)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        FilledIconButton(
                            onClick = {
                                if (inputText.isNotBlank()) {
                                    viewModel.sendChatMessage(inputText)
                                    inputText = ""
                                }
                            },
                            enabled = !isLoading && inputText.isNotBlank() && currentModelId != null,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = Color(0xFFFFD700),
                                disabledContainerColor = Color(0xFF8B7355)
                            )
                        ) {
                            Icon(
                                Icons.Default.Send,
                                "Send",
                                tint = Color(0xFF2C1810)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SirRemiChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier.widthIn(max = 300.dp),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isUser) 16.dp else 4.dp,
                bottomEnd = if (message.isUser) 4.dp else 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isUser)
                    Color(0xFF4A2F1F)
                else
                    Color(0xFF2C1810)
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = if (message.isUser) "You" else "🤖 Sir Remi",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (message.isUser)
                        Color(0xFFFFD700)
                    else
                        Color(0xFFFFD700)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.text,
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun SirRemiEmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🤖", fontSize = 72.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Greetings, Brave Knight!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFD700)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "I am Sir Remi, your friendly study buddy! I can help you with:",
            fontSize = 14.sp,
            color = Color(0xFFE0E0E0),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))

        // List of capabilities
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            CapabilityItem("📝", "Summarize your study notes and PDFs")
            CapabilityItem("❓", "Answer questions about any topic")
            CapabilityItem("📚", "Explain complex concepts simply")
            CapabilityItem("💡", "Provide study tips and strategies")
            CapabilityItem("🎯", "Break down problems step-by-step")
            CapabilityItem("💬", "Chat about everyday topics")
        }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Connect your Google Drive to access your notes, or just start asking questions!",
            fontSize = 12.sp,
            color = Color(0xFF8B7355),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
fun CapabilityItem(icon: String, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            icon,
            fontSize = 20.sp,
            modifier = Modifier.width(32.dp)
        )
        Text(
            text,
            fontSize = 13.sp,
            color = Color(0xFFE0E0E0)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIChatScreen(
    viewModel: EduVentureViewModel,
    onBack: () -> Unit
) {
    val chatMessages by viewModel.chatMessages.collectAsState()
    val isLoading by viewModel.isAIChatLoading.collectAsState()
    val statusMessage by viewModel.statusMessage.collectAsState()
    val currentModelId by viewModel.currentModelId.collectAsState()
    val availableModels by viewModel.availableModels.collectAsState()
    val downloadProgress by viewModel.downloadProgress.collectAsState()

    var inputText by remember { mutableStateOf("") }
    var showModelSelector by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "AI Study Companion",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            statusMessage,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { showModelSelector = !showModelSelector }) {
                        Text("AI Model", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6366F1)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF9FAFB))
        ) {
            // Model Selector (collapsible)
            if (showModelSelector) {
                ModelSelectorPanel(
                    availableModels = availableModels,
                    currentModelId = currentModelId,
                    downloadProgress = downloadProgress,
                    onDownload = { modelId -> viewModel.downloadModel(modelId) },
                    onLoad = { modelId -> viewModel.loadModel(modelId) },
                    onRefresh = { viewModel.refreshModels() }
                )
            }

            // Messages List
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (chatMessages.isEmpty()) {
                    item {
                        EmptyChatState()
                    }
                }

                items(chatMessages) { message ->
                    ChatMessageBubble(message)
                }
            }

            // Auto-scroll to bottom
            LaunchedEffect(chatMessages.size) {
                if (chatMessages.isNotEmpty()) {
                    listState.animateScrollToItem(chatMessages.size - 1)
                }
            }

            // Input Field
            Surface(
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Ask me anything about your studies...") },
                        enabled = !isLoading && currentModelId != null,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF3F4F6),
                            unfocusedContainerColor = Color(0xFFF3F4F6),
                            disabledContainerColor = Color(0xFFF3F4F6)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    FilledIconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                viewModel.sendChatMessage(inputText)
                                inputText = ""
                            }
                        },
                        enabled = !isLoading && inputText.isNotBlank() && currentModelId != null,
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Color(0xFF6366F1)
                        )
                    ) {
                        Icon(Icons.Default.Send, "Send", tint = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ChatMessageBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 300.dp),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isUser) 16.dp else 4.dp,
                bottomEnd = if (message.isUser) 4.dp else 16.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isUser)
                    Color(0xFF6366F1)
                else
                    Color.White
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = if (message.isUser) "You" else "AI Assistant",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (message.isUser)
                        Color.White.copy(alpha = 0.8f)
                    else
                        Color(0xFF6B7280)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.text,
                    fontSize = 14.sp,
                    color = if (message.isUser) Color.White else Color(0xFF1F2937),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun EmptyChatState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🤖", fontSize = 72.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Hi! I'm your AI Study Companion",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Ask me questions about your studies, request explanations, or get help with homework!",
            fontSize = 14.sp,
            color = Color(0xFF6B7280)
        )
    }
}

@Composable
fun ModelSelectorPanel(
    availableModels: List<com.runanywhere.sdk.models.ModelInfo>,
    currentModelId: String?,
    downloadProgress: Float?,
    onDownload: (String) -> Unit,
    onLoad: (String) -> Unit,
    onRefresh: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "AI Model",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
                TextButton(onClick = onRefresh) {
                    Text("Refresh")
                }
            }

            downloadProgress?.let { progress ->
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    color = Color(0xFF6366F1)
                )
            }

            if (availableModels.isEmpty()) {
                Text(
                    "Loading models...",
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                availableModels.forEach { model ->
                    ModelItemCompact(
                        model = model,
                        isLoaded = model.id == currentModelId,
                        onDownload = { onDownload(model.id) },
                        onLoad = { onLoad(model.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ModelItemCompact(
    model: com.runanywhere.sdk.models.ModelInfo,
    isLoaded: Boolean,
    onDownload: () -> Unit,
    onLoad: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                model.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1F2937)
            )
            if (isLoaded) {
                Text(
                    "✓ Loaded",
                    fontSize = 12.sp,
                    color = Color(0xFF10B981)
                )
            }
        }

        if (!isLoaded) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (!model.isDownloaded) {
                    TextButton(onClick = onDownload) {
                        Text("Download")
                    }
                } else {
                    TextButton(onClick = onLoad) {
                        Text("Load")
                    }
                }
            }
        }
    }
}

@Composable
fun PDFSelectorPanel(
    availablePDFs: List<com.runanywhere.startup_hackathon20.data.repository.DriveFile>,
    selectedPDF: com.runanywhere.startup_hackathon20.data.repository.DriveFile?,
    onSelectPDF: (com.runanywhere.startup_hackathon20.data.repository.DriveFile) -> Unit,
    onSummarizePDF: () -> Unit,
    onSetFolderId: (String) -> Unit,
    onRefresh: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF4A2F1F),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "📚 Study Notes from Drive",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD700)
                )

                TextButton(onClick = onRefresh) {
                    Text("Refresh", color = Color(0xFFFFD700))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Selected PDF
            selectedPDF?.let { pdf ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2C1810)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "✓ ${pdf.name}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFD700)
                            )
                            Text(
                                "${pdf.size / 1024} KB",
                                fontSize = 12.sp,
                                color = Color(0xFFC0C0C0)
                            )
                        }

                        Button(
                            onClick = onSummarizePDF,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFD700)
                            )
                        ) {
                            Text("Summarize", color = Color(0xFF2C1810), fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            // PDF List
            if (availablePDFs.isNotEmpty()) {
                Text(
                    "Available Notes (${availablePDFs.size})",
                    fontSize = 14.sp,
                    color = Color(0xFFC0C0C0),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                availablePDFs.take(5).forEach { pdf ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (pdf.id == selectedPDF?.id)
                                Color(0xFF5C3D2E)
                            else
                                Color(0xFF3D2417)
                        ),
                        onClick = { onSelectPDF(pdf) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    pdf.name,
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                                Text(
                                    "${pdf.size / 1024} KB",
                                    fontSize = 11.sp,
                                    color = Color(0xFF8B7355)
                                )
                            }

                            if (pdf.id == selectedPDF?.id) {
                                Text("✓", fontSize = 16.sp, color = Color(0xFFFFD700))
                            }
                        }
                    }
                }

                if (availablePDFs.size > 5) {
                    Text(
                        "+${availablePDFs.size - 5} more files...",
                        fontSize = 12.sp,
                        color = Color(0xFF8B7355),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2C1810)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📂", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "No PDFs Found",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Make sure your Drive folder is publicly accessible",
                            fontSize = 12.sp,
                            color = Color(0xFFC0C0C0),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
