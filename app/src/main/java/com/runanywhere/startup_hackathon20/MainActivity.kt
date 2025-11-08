package com.runanywhere.startup_hackathon20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.runanywhere.startup_hackathon20.data.models.UserRole
import com.runanywhere.startup_hackathon20.data.repository.RPGRepository
import com.runanywhere.startup_hackathon20.ui.screens.*
import com.runanywhere.startup_hackathon20.ui.theme.Startup_hackathon20Theme
import com.runanywhere.startup_hackathon20.viewmodel.EduVentureViewModel
import com.runanywhere.startup_hackathon20.viewmodel.EduVentureViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Startup_hackathon20Theme {
                EduVentureApp()
            }
        }
    }
}

sealed class Screen {
    object RoleSelection : Screen()
    object StudentLogin : Screen()
    object TeacherLogin : Screen()
    object StudentHome : Screen()
    object TeacherHome : Screen()
}

@Composable
fun EduVentureApp() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val viewModel: EduVentureViewModel = viewModel(
        factory = EduVentureViewModelFactory(context)
    )

    val currentUser by viewModel.currentUser.collectAsState()
    val knightProfile by viewModel.knightProfile.collectAsState()
    val learningRoutes by viewModel.learningRoutes.collectAsState()
    val loginError by viewModel.loginError.collectAsState()

    var currentScreen by remember { mutableStateOf<Screen>(Screen.RoleSelection) }
    var selectedModule by remember {
        mutableStateOf<com.runanywhere.startup_hackathon20.data.models.QuestModule?>(null)
    }

    when (currentScreen) {
        Screen.RoleSelection -> {
            LoginScreen(
                viewModel = viewModel,
                onLoginAsStudent = {
                    currentScreen = Screen.StudentLogin
                },
                onLoginAsTeacher = {
                    currentScreen = Screen.TeacherLogin
                }
            )
        }

        Screen.StudentLogin -> {
            StudentLoginScreen(
                onBack = { currentScreen = Screen.RoleSelection },
                onLogin = { studentId, password ->
                    coroutineScope.launch {
                        val success = viewModel.loginStudent(studentId, password)
                        if (success) {
                            currentScreen = Screen.StudentHome
                        }
                    }
                },
                errorMessage = loginError
            )
        }

        Screen.TeacherLogin -> {
            TeacherLoginScreen(
                onBack = { currentScreen = Screen.RoleSelection },
                onLogin = { teacherId, password ->
                    coroutineScope.launch {
                        val success = viewModel.loginTeacher(teacherId, password)
                        if (success) {
                            currentScreen = Screen.TeacherHome
                        }
                    }
                },
                errorMessage = loginError
            )
        }

        Screen.StudentHome -> {
            // Check if we need to show module video
            selectedModule?.let { module ->
                ModuleVideoScreen(
                    module = module,
                    onBack = {
                        selectedModule = null
                    },
                    onComplete = {
                        coroutineScope.launch {
                            viewModel.completeModule(module.id)
                        }
                        selectedModule = null
                    }
                )
            } ?: run {
                // Show the main RPG student screen with navigation
                RPGStudentScreenWithNav(
                    knightProfile = knightProfile,
                    routes = learningRoutes,
                    onModuleClick = { module ->
                        selectedModule = module
                    },
                    viewModel = viewModel,
                    onLogout = {
                        viewModel.logout()
                        currentScreen = Screen.RoleSelection
                    }
                )
            }
        }

        Screen.TeacherHome -> {
            RPGTeacherScreenWithNav(
                viewModel = viewModel,
                onLogout = {
                    viewModel.logout()
                    currentScreen = Screen.RoleSelection
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Startup_hackathon20Theme {
        EduVentureApp()
    }
}