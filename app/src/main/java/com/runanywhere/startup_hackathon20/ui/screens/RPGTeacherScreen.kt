package com.runanywhere.startup_hackathon20.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.runanywhere.startup_hackathon20.data.models.*
import com.runanywhere.startup_hackathon20.viewmodel.EduVentureViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RPGTeacherScreenWithNav(
    viewModel: EduVentureViewModel,
    onLogout: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }
    var currentFeatureScreen by remember { mutableStateOf<String?>(null) }
    val teacherCourses by viewModel.teacherCourses.collectAsState()
    val classRooms by viewModel.classRooms.collectAsState()
    val sharedResources by viewModel.sharedResources.collectAsState()
    val user by viewModel.currentUser.collectAsState()

    // If a feature screen is selected, show it instead of the main tabs
    if (currentFeatureScreen != null) {
        when (currentFeatureScreen) {
            "tech_courses" -> TechDevelopmentCoursesScreen(
                viewModel = viewModel,
                onCourseClick = { /* Handle course click */ }
            )

            "resource_hub" -> ResourceHubScreen(viewModel = viewModel)
            "student_insights" -> StudentPerformanceInsightsScreen(viewModel = viewModel)
            "progress_dashboard" -> ProgressDashboardScreen(viewModel = viewModel)
        }

        // Back button handling
        BackHandler {
            currentFeatureScreen = null
        }
        return
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF2C1810),
                contentColor = Color(0xFFFFD700)
            ) {
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            "Home",
                            tint = if (selectedTab == 0) Color(0xFFFFD700) else Color(0xFF8B7355)
                        )
                    },
                    label = {
                        Text(
                            "Home",
                            color = if (selectedTab == 0) Color(0xFFFFD700) else Color(0xFF8B7355),
                            fontSize = 11.sp
                        )
                    },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFFD700),
                        selectedTextColor = Color(0xFFFFD700),
                        indicatorColor = Color(0xFF4A2F1F),
                        unselectedIconColor = Color(0xFF8B7355),
                        unselectedTextColor = Color(0xFF8B7355)
                    )
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            "Students",
                            tint = if (selectedTab == 1) Color(0xFFFFD700) else Color(0xFF8B7355)
                        )
                    },
                    label = {
                        Text(
                            "Students",
                            color = if (selectedTab == 1) Color(0xFFFFD700) else Color(0xFF8B7355),
                            fontSize = 11.sp
                        )
                    },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFFD700),
                        selectedTextColor = Color(0xFFFFD700),
                        indicatorColor = Color(0xFF4A2F1F),
                        unselectedIconColor = Color(0xFF8B7355),
                        unselectedTextColor = Color(0xFF8B7355)
                    )
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Menu,
                            "Resources",
                            tint = if (selectedTab == 2) Color(0xFFFFD700) else Color(0xFF8B7355)
                        )
                    },
                    label = {
                        Text(
                            "Resources",
                            color = if (selectedTab == 2) Color(0xFFFFD700) else Color(0xFF8B7355),
                            fontSize = 11.sp
                        )
                    },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFFD700),
                        selectedTextColor = Color(0xFFFFD700),
                        indicatorColor = Color(0xFF4A2F1F),
                        unselectedIconColor = Color(0xFF8B7355),
                        unselectedTextColor = Color(0xFF8B7355)
                    )
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.DateRange,
                            "Timetables",
                            tint = if (selectedTab == 3) Color(0xFFFFD700) else Color(0xFF8B7355)
                        )
                    },
                    label = {
                        Text(
                            "Timetables",
                            color = if (selectedTab == 3) Color(0xFFFFD700) else Color(0xFF8B7355),
                            fontSize = 11.sp
                        )
                    },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFFD700),
                        selectedTextColor = Color(0xFFFFD700),
                        indicatorColor = Color(0xFF4A2F1F),
                        unselectedIconColor = Color(0xFF8B7355),
                        unselectedTextColor = Color(0xFF8B7355)
                    )
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Settings,
                            "Profile",
                            tint = if (selectedTab == 4) Color(0xFFFFD700) else Color(0xFF8B7355)
                        )
                    },
                    label = {
                        Text(
                            "Profile",
                            color = if (selectedTab == 4) Color(0xFFFFD700) else Color(0xFF8B7355),
                            fontSize = 11.sp
                        )
                    },
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFFFFD700),
                        selectedTextColor = Color(0xFFFFD700),
                        indicatorColor = Color(0xFF4A2F1F),
                        unselectedIconColor = Color(0xFF8B7355),
                        unselectedTextColor = Color(0xFF8B7355)
                    )
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (selectedTab) {
                0 -> RPGTeacherDashboard(
                    user = user,
                    classRooms = classRooms,
                    teacherCourses = teacherCourses,
                    onFeatureClick = { feature ->
                        currentFeatureScreen = feature
                    }
                )

                1 -> RPGStudentsTab(classRooms = classRooms)
                2 -> RPGResourcesTab(resources = sharedResources)
                3 -> RPGClassTimetablesTab(classRooms = classRooms)
                4 -> RPGTeacherProfile(user = user, onLogout = onLogout)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RPGTeacherDashboard(
    user: User?,
    classRooms: List<ClassRoom>,
    teacherCourses: List<TeacherCourse>,
    onFeatureClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "🏰 Master's Hall",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Teacher Profile Card
                item {
                    user?.let {
                        TeacherMasterCard(it)
                    }
                }

                // Stats Overview
                item {
                    TeacherStatsOverview(classRooms)
                }

                // Professional Development
                item {
                    Text(
                        "📚 Professional Development",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700)
                    )
                }

                items(teacherCourses) { course ->
                    RPGTeacherCourseCard(course)
                }
            }
        }
    }
}

@Composable
fun TeacherMasterCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF4A2F1F)
        )
    ) {
        Box {
            // Background pattern
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
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "🎓 Master ${user.name}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700)
                        )
                        Text(
                            "Knowledge Keeper",
                            fontSize = 16.sp,
                            color = Color(0xFFC0C0C0),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Master Badge
                    Box(
                        modifier = Modifier
                            .size(70.dp)
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
                            .border(3.dp, Color(0xFF4A2F1F), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "📜",
                                fontSize = 20.sp
                            )
                            Text(
                                "Lv ${user.level}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // XP Progress
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "⭐ Mastery XP",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700)
                        )
                        Text(
                            "${user.xp} XP",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { (user.xp % 100) / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .border(2.dp, Color(0xFF8B4513), RoundedCornerShape(6.dp)),
                        color = Color(0xFFFFD700),
                        trackColor = Color(0xFF3D2417)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Achievements Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TeacherStatBox("🏅", "Courses", "${user.subjects.size}")
                    TeacherStatBox("🌟", "Streak", "${user.streak}")
                    TeacherStatBox("🎖️", "Badges", "${user.achievements.size}")
                }
            }
        }
    }
}

@Composable
fun TeacherStatBox(icon: String, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(icon, fontSize = 20.sp)
        Text(
            value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFD700)
        )
        Text(
            label,
            fontSize = 11.sp,
            color = Color(0xFFC0C0C0)
        )
    }
}

@Composable
fun TeacherStatsOverview(classRooms: List<ClassRoom>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2C1810)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                "📊 Kingdom Overview",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700)
            )

            Spacer(modifier = Modifier.height(16.dp))

            val totalStudents = classRooms.sumOf { it.students.size }
            val avgLevel = if (totalStudents > 0) {
                classRooms.flatMap { it.students }.map { it.level }.average().toInt()
            } else 0
            val activeStudents = classRooms.flatMap { it.students }
                .count { System.currentTimeMillis() - it.lastActive < 86400000 }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OverviewStatBox("👥", "Students", "$totalStudents")
                OverviewStatBox("⚔️", "Avg Level", "$avgLevel")
                OverviewStatBox("✅", "Active", "$activeStudents")
            }
        }
    }
}

@Composable
fun OverviewStatBox(icon: String, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(icon, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            value,
            fontSize = 20.sp,
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
fun RPGClassRoomCard(classroom: ClassRoom) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2C1810)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "🏛️ ${classroom.name}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        classroom.subject,
                        fontSize = 14.sp,
                        color = Color(0xFFC0C0C0)
                    )
                }

                Surface(
                    color = Color(0xFFFFD700).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "${classroom.students.size} Knights",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ClassStatBox(
                    "⚔️",
                    "Avg Lv",
                    "${classroom.students.map { it.level }.average().toInt()}"
                )
                ClassStatBox(
                    "🎯",
                    "Avg Score",
                    "${classroom.students.map { it.averageScore }.average().toInt()}%"
                )
                ClassStatBox(
                    "🔥",
                    "Active",
                    "${classroom.students.count { System.currentTimeMillis() - it.lastActive < 86400000 }}"
                )
            }
        }
    }
}

@Composable
fun ClassStatBox(icon: String, label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(icon, fontSize = 18.sp)
        Text(
            value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFD700)
        )
        Text(
            label,
            fontSize = 11.sp,
            color = Color(0xFF8B7355)
        )
    }
}

@Composable
fun RPGTeacherCourseCard(course: TeacherCourse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2C1810)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "📚 ${course.title}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        course.category,
                        fontSize = 14.sp,
                        color = Color(0xFFC0C0C0)
                    )
                }

                Surface(
                    color = Color(0xFFFFD700).copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "+${course.xpReward} XP",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFD700)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${(course.progress * 100).toInt()}% Complete",
                        fontSize = 12.sp,
                        color = Color(0xFFC0C0C0)
                    )
                    Text(
                        "${course.modules.size} Modules",
                        fontSize = 12.sp,
                        color = Color(0xFF8B7355)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { course.progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, Color(0xFF8B4513), RoundedCornerShape(4.dp)),
                    color = Color(0xFFFFD700),
                    trackColor = Color(0xFF3D2417)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RPGStudentsTab(classRooms: List<ClassRoom>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "👥 Students",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(classRooms) { classroom ->
                    RPGClassDetailCard(classroom)
                }
            }
        }
    }
}

@Composable
fun RPGClassDetailCard(classroom: ClassRoom) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2C1810)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                "🏛️ ${classroom.name}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700)
            )
            Text(
                classroom.subject,
                fontSize = 14.sp,
                color = Color(0xFFC0C0C0),
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "⚔️ Knights in Training",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFD700)
            )

            Spacer(modifier = Modifier.height(12.dp))

            classroom.students.take(5).forEach { student ->
                RPGStudentProgressCard(student)
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (classroom.students.size > 5) {
                Text(
                    "+${classroom.students.size - 5} more knights...",
                    fontSize = 12.sp,
                    color = Color(0xFF8B7355),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun RPGStudentProgressCard(student: StudentProgress) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF4A2F1F)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFFFD700),
                                    Color(0xFF8B4513)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "🛡️",
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        student.studentName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Level ${student.level} • ${student.completedQuests} Quests",
                        fontSize = 11.sp,
                        color = Color(0xFFC0C0C0)
                    )
                }
            }

            Surface(
                color = when {
                    student.averageScore >= 80 -> Color(0xFF10B981)
                    student.averageScore >= 60 -> Color(0xFFF59E0B)
                    else -> Color(0xFFEF4444)
                }.copy(alpha = 0.2f),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    "${student.averageScore.toInt()}%",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        student.averageScore >= 80 -> Color(0xFF10B981)
                        student.averageScore >= 60 -> Color(0xFFF59E0B)
                        else -> Color(0xFFEF4444)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RPGClassTimetablesTab(classRooms: List<ClassRoom>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "🕒 Class Timetables",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(classRooms) { classroom ->
                    RPGClassDetailCard(classroom)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RPGResourcesTab(resources: List<SharedResource>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "📚 Knowledge Library",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(resources) { resource ->
                    RPGResourceCard(resource)
                }

                if (resources.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("📜", fontSize = 48.sp)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    "No resources yet",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFD700)
                                )
                                Text(
                                    "Share your teaching materials with other masters",
                                    fontSize = 14.sp,
                                    color = Color(0xFFC0C0C0),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RPGResourceCard(resource: SharedResource) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2C1810)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text("📜", fontSize = 32.sp, modifier = Modifier.padding(end = 12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    resource.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD700)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "by Master ${resource.teacherName}",
                    fontSize = 12.sp,
                    color = Color(0xFF8B7355)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        color = Color(0xFFFFD700).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            resource.subject,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 11.sp,
                            color = Color(0xFFFFD700)
                        )
                    }
                    Text(
                        "⭐ ${resource.rating}",
                        fontSize = 12.sp,
                        color = Color(0xFFC0C0C0)
                    )
                    Text(
                        "↓ ${resource.downloads}",
                        fontSize = 12.sp,
                        color = Color(0xFFC0C0C0)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RPGTeacherProfile(user: User?, onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "👤 Master Profile",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    user?.let {
                        TeacherMasterCard(it)
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(6.dp, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2C1810)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Text(
                                "🎖️ Achievements",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFD700)
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            user?.achievements?.forEach { achievement ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(achievement.iconName, fontSize = 24.sp)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            achievement.name,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                        Text(
                                            achievement.description,
                                            fontSize = 12.sp,
                                            color = Color(0xFFC0C0C0)
                                        )
                                    }
                                }
                            }

                            if (user?.achievements?.isEmpty() == true) {
                                Text(
                                    "Complete courses to earn achievements",
                                    fontSize = 14.sp,
                                    color = Color(0xFF8B7355)
                                )
                            }
                        }
                    }
                }

                item {
                    Button(
                        onClick = onLogout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF8B0000)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Leave Hall",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TeacherFeatureCard(
    icon: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C1810))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFFFFD700),
                                Color(0xFF8B4513)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(icon, fontSize = 32.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD700)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    description,
                    fontSize = 13.sp,
                    color = Color(0xFFC0C0C0),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Open",
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
