package com.runanywhere.startup_hackathon20.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherLoginScreen(
    onBack: () -> Unit,
    onLogin: (String, String) -> Unit,
    errorMessage: String? = null
) {
    var teacherId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Back button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = onBack,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color(0xFFFFD700)
                    )
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            // Title Section
            Text(
                "🎓",
                fontSize = 80.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                "Master's Portal",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                "Access your teaching realm",
                fontSize = 16.sp,
                color = Color(0xFFC0C0C0),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            // Login Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2C1810)
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Teacher Login",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Teacher ID Field
                    OutlinedTextField(
                        value = teacherId,
                        onValueChange = { teacherId = it },
                        label = { Text("Teacher ID", color = Color(0xFFC0C0C0)) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Teacher ID",
                                tint = Color(0xFFFFD700)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color(0xFF3D2417),
                            unfocusedContainerColor = Color(0xFF3D2417),
                            focusedBorderColor = Color(0xFFFFD700),
                            unfocusedBorderColor = Color(0xFF8B7355),
                            cursorColor = Color(0xFFFFD700)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    // Password Field
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", color = Color(0xFFC0C0C0)) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Password",
                                tint = Color(0xFFFFD700)
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color(0xFF3D2417),
                            unfocusedContainerColor = Color(0xFF3D2417),
                            focusedBorderColor = Color(0xFFFFD700),
                            unfocusedBorderColor = Color(0xFF8B7355),
                            cursorColor = Color(0xFFFFD700)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )

                    // Error Message
                    errorMessage?.let {
                        Text(
                            text = it,
                            color = Color(0xFFFF6B6B),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    // Login Button
                    Button(
                        onClick = {
                            if (teacherId.isNotBlank() && password.isNotBlank()) {
                                onLogin(teacherId, password)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFD700),
                            contentColor = Color(0xFF2C1810)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = teacherId.isNotBlank() && password.isNotBlank()
                    ) {
                        Text(
                            "Enter Teaching Realm",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Demo credentials hint
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF4A2F1F)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                "📝 Demo Credentials",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFD700)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "ID: TEACH001",
                                fontSize = 11.sp,
                                color = Color(0xFFC0C0C0)
                            )
                            Text(
                                "Password: master123",
                                fontSize = 11.sp,
                                color = Color(0xFFC0C0C0)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Footer text
            Text(
                "Welcome back, Master Teacher!",
                fontSize = 14.sp,
                color = Color(0xFF8B7355),
                textAlign = TextAlign.Center
            )
        }
    }
}
