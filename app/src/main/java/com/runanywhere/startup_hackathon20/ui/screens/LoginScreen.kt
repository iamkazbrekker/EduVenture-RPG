package com.runanywhere.startup_hackathon20.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.runanywhere.startup_hackathon20.viewmodel.EduVentureViewModel

@Composable
fun LoginScreen(
    viewModel: EduVentureViewModel,
    onLoginAsStudent: () -> Unit,
    onLoginAsTeacher: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A0F0A),
                        Color(0xFF2C1810),
                        Color(0xFF3D2417),
                        Color(0xFF4A2F1F)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo/Title
            Text(
                text = "🏰",
                fontSize = 72.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "EduVenture",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Embark on Your Learning Quest",
                fontSize = 18.sp,
                color = Color(0xFFC0C0C0),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Role Selection Cards
            RoleSelectionCard(
                title = "Student Knight",
                icon = "⚔️",
                description = "Embark on learning quests, earn XP, and unlock legendary achievements",
                onClick = {
                    viewModel.loginAsStudent()
                    onLoginAsStudent()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleSelectionCard(
                title = "Master Teacher",
                icon = "🎓",
                description = "Guide young knights, access development courses, and track their progress",
                onClick = {
                    viewModel.loginAsTeacher()
                    onLoginAsTeacher()
                }
            )
        }
    }
}

@Composable
fun RoleSelectionCard(
    title: String,
    icon: String,
    description: String,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2C1810)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isPressed) 2.dp else 8.dp
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFFD700),
                                Color(0xFFDAA520),
                                Color(0xFF8B4513)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icon,
                    fontSize = 32.sp
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD700)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color(0xFFC0C0C0),
                    lineHeight = 20.sp
                )
            }
        }
    }
}
