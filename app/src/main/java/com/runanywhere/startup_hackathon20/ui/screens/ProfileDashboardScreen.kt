package com.runanywhere.startup_hackathon20.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.runanywhere.startup_hackathon20.BuildConfig
import com.runanywhere.startup_hackathon20.data.models.KnightProfile
import com.runanywhere.startup_hackathon20.viewmodel.EduVentureViewModel
import kotlinx.coroutines.launch

data class ProfileOption(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDashboardScreen(
    knightProfile: KnightProfile?,
    viewModel: EduVentureViewModel? = null,
    onEditProfile: () -> Unit = {},
    onChangePassword: () -> Unit = {},
    onContactUs: () -> Unit = {},
    onShop: () -> Unit = {},
    onNotifications: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    var showEditProfile by remember { mutableStateOf(false) }
    var showChangePassword by remember { mutableStateOf(false) }
    var showResetConfirmation by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Header Card
            item {
                knightProfile?.let { knight ->
                    ProfileHeaderCard(knight)
                }
            }

            // Profile Options
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4A2F1F)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            "⚙️ Account Settings",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700),
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                        )

                        ProfileOptionItem(
                            icon = Icons.Default.Edit,
                            title = "Edit Profile",
                            subtitle = "Update your knight information",
                            onClick = {
                                showEditProfile = true
                                onEditProfile()
                            }
                        )

                        Divider(
                            color = Color(0xFF2C1810),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        ProfileOptionItem(
                            icon = Icons.Default.Lock,
                            title = "Change Password",
                            subtitle = "Secure your account",
                            onClick = {
                                showChangePassword = true
                                onChangePassword()
                            }
                        )

                        Divider(
                            color = Color(0xFF2C1810),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        ProfileOptionItem(
                            icon = Icons.Default.Notifications,
                            title = "Notifications",
                            subtitle = "Manage your notification preferences",
                            onClick = onNotifications
                        )
                    }
                }
            }

            // Additional Options
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4A2F1F)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            "🛡️ Kingdom Services",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700),
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                        )

                        ProfileOptionItem(
                            icon = Icons.Default.ShoppingCart,
                            title = "Shop",
                            subtitle = "Purchase weapons and armor",
                            onClick = onShop
                        )

                        Divider(
                            color = Color(0xFF2C1810),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        ProfileOptionItem(
                            icon = Icons.Default.Email,
                            title = "Contact Us",
                            subtitle = "Get help from the guild",
                            onClick = onContactUs
                        )
                    }
                }
            }

            // Debug Options
            item {
                if (BuildConfig.DEBUG) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF4A2F1F)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp)
                        ) {
                            Text(
                                "🔍 Debug Options",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFD700),
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                            )

                            ProfileOptionItem(
                                icon = Icons.Default.Delete,
                                title = "Reset Database",
                                subtitle = "Clear all data and repopulate with demo accounts",
                                onClick = {
                                    showResetConfirmation = true
                                }
                            )
                        }
                    }
                }
            }

            // Logout Button
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF8B0000)
                    ),
                    onClick = onLogout
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Logout",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            // Spacer at bottom
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Show dialogs
    if (showEditProfile && knightProfile != null && viewModel != null) {
        EditProfileDialog(
            knight = knightProfile,
            onDismiss = { showEditProfile = false },
            onSave = { name, email, phone ->
                viewModel.updateProfile(name, email, phone)
                showEditProfile = false
            }
        )
    }

    if (showChangePassword && viewModel != null) {
        ChangePasswordDialog(
            onDismiss = { showChangePassword = false },
            onChangePassword = { old, new ->
                viewModel.changePassword(old, new) { result ->
                    // Handle result - could show a toast or snackbar
                }
            }
        )
    }

    if (showResetConfirmation) {
        AlertDialog(
            onDismissRequest = { showResetConfirmation = false },
            title = { Text("Reset Database") },
            text = { Text("Are you sure you want to reset the database?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel?.resetDatabase()
                        showResetConfirmation = false
                    }
                ) {
                    Text("Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmation = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ProfileHeaderCard(knight: KnightProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF4A2F1F)
        )
    ) {
        Box {
            // Background gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF5C3D2E),
                                Color(0xFF4A2F1F),
                                Color(0xFF5C3D2E)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFFFD700),
                                    Color(0xFFDAA520),
                                    Color(0xFF8B4513)
                                )
                            )
                        )
                        .border(4.dp, Color(0xFF2C1810), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "🛡️",
                        fontSize = 48.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    knight.knightName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD700)
                )

                Text(
                    knight.rank.title,
                    fontSize = 16.sp,
                    color = Color(0xFFC0C0C0),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Stats Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileStat("⭐", "XP", "${knight.totalXP}")
                    ProfileStat("⚔️", "Victories", "${knight.defeatedEnemies.size}")
                    ProfileStat("🏅", "Badges", "${knight.badges.size}")
                }
            }
        }
    }
}

@Composable
fun ProfileStat(icon: String, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(icon, fontSize = 24.sp)
        Text(
            value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFD700)
        )
        Text(
            label,
            fontSize = 12.sp,
            color = Color(0xFFC0C0C0)
        )
    }
}

@Composable
fun ProfileOptionItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    subtitle,
                    fontSize = 13.sp,
                    color = Color(0xFFC0C0C0)
                )
            }

            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Go",
                tint = Color(0xFF8B4513),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun EditProfileDialog(
    knight: KnightProfile,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var name by remember { mutableStateOf(knight.knightName) }
    var email by remember { mutableStateOf(knight.email) }
    var phone by remember { mutableStateOf(knight.phoneNumber) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Profile", color = Color.White) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Knight Name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFFFD700),
                        unfocusedBorderColor = Color(0xFF8B7355),
                        focusedLabelColor = Color(0xFFFFD700),
                        unfocusedLabelColor = Color(0xFFC0C0C0)
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFFFD700),
                        unfocusedBorderColor = Color(0xFF8B7355),
                        focusedLabelColor = Color(0xFFFFD700),
                        unfocusedLabelColor = Color(0xFFC0C0C0)
                    )
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFFFD700),
                        unfocusedBorderColor = Color(0xFF8B7355),
                        focusedLabelColor = Color(0xFFFFD700),
                        unfocusedLabelColor = Color(0xFFC0C0C0)
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(name, email, phone) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD700),
                    contentColor = Color(0xFF2C1810)
                )
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color(0xFFC0C0C0))
            }
        },
        containerColor = Color(0xFF2C1810),
        textContentColor = Color.White
    )
}

@Composable
fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onChangePassword: (String, String) -> Unit
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change Password", color = Color.White) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    label = { Text("Old Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFFFD700),
                        unfocusedBorderColor = Color(0xFF8B7355),
                        focusedLabelColor = Color(0xFFFFD700),
                        unfocusedLabelColor = Color(0xFFC0C0C0)
                    )
                )
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFFFD700),
                        unfocusedBorderColor = Color(0xFF8B7355),
                        focusedLabelColor = Color(0xFFFFD700),
                        unfocusedLabelColor = Color(0xFFC0C0C0)
                    )
                )
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color(0xFFFFD700),
                        unfocusedBorderColor = Color(0xFF8B7355),
                        focusedLabelColor = Color(0xFFFFD700),
                        unfocusedLabelColor = Color(0xFFC0C0C0)
                    )
                )
                error?.let {
                    Text(it, color = Color(0xFFFF6B6B), fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    when {
                        newPassword != confirmPassword -> error = "Passwords don't match"
                        newPassword.length < 6 -> error = "Password too short"
                        else -> {
                            onChangePassword(oldPassword, newPassword)
                            onDismiss()
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD700),
                    contentColor = Color(0xFF2C1810)
                )
            ) {
                Text("Change Password")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color(0xFFC0C0C0))
            }
        },
        containerColor = Color(0xFF2C1810),
        textContentColor = Color.White
    )
}
