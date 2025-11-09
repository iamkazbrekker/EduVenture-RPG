# 🎮 EduVenture RPG - Gamified Learning Platform

> Transform education into an epic adventure! A comprehensive learning management system that
> combines RPG elements, AI tutoring, and dynamic content delivery to make learning engaging and
> effective.

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg)](https://developer.android.com/about/versions/nougat)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Architecture](#-architecture)
- [Technology Stack](#-technology-stack)
- [Getting Started](#-getting-started)
- [Project Structure](#-project-structure)
- [RPG System](#-rpg-system)
- [AI Integration](#-ai-integration)
- [Video Learning](#-video-learning)
- [Database Schema](#-database-schema)
- [Configuration](#-configuration)
- [Roadmap](#-roadmap)
---

## 🌟 Overview

**EduVenture RPG** reimagines education as an epic adventure where students become knights on a
quest for knowledge. By combining gamification mechanics, AI-powered tutoring, and dynamic content
delivery, we've created an engaging platform that motivates students while providing teachers with
powerful analytics and management tools.

### The Problem We Solve

Traditional learning platforms lack engagement. Students often find educational content disconnected
from their interests, leading to decreased motivation and poor retention.

### Our Solution

**EduVenture RPG** transforms learning into an adventure by combining:

- 🎮 **RPG Gamification** - Students are knights fighting enemies (learning modules)
- 🤖 **AI-Powered Tutoring** - On-device AI assistant for personalized help
- ☁️ **Dynamic Content** - Learning routes fetched from Google Drive for easy updates
- 🏆 **Social Competition** - Leaderboards and achievements to drive engagement
- 📹 **Video Integration** - YouTube content embedded in each learning module

---

## ✨ Features

### 🎓 For Students

#### RPG Progression System

- **Knight Profiles**: Create customizable knight characters
- **XP & Ranking**: Progress from Novice → Squire → Knight → Hero
- **Weapon Upgrades**: Unlock better weapons (Wooden → Iron → Golden → Diamond Sword)
- **Enemy Battles**: Each learning module is an enemy to defeat
- **Achievement Badges**: Earn badges for milestones and accomplishments
- **Daily Streaks**: Maintain engagement with streak tracking

#### Learning Features

- **Dynamic Learning Routes**: Subjects updated via Google Drive
- **Video Lessons**: Embedded YouTube player with custom time segments
- **Progress Tracking**: Visual progress bars and completion tracking
- **Module Completion**: Mark modules as complete to earn XP
- **Subject Variety**: Math, Physics, Chemistry, and more

#### AI Study Companion

- **On-Device AI**: RunAnywhere SDK with LLaMA models
- **Contextual Help**: Get explanations about course content
- **Study Material Analysis**: AI can read and discuss PDFs
- **Practice Problems**: Generate custom exercises
- **Offline Capable**: AI runs entirely on-device

### 👨‍🏫 For Teachers (Planned)

- **Student Analytics**: Monitor progress and performance
- **Content Management**: Create and manage learning routes
- **Class Dashboard**: View class-wide statistics
- **Resource Library**: Share teaching materials
- **Professional Development**: Gamified teacher training

### 🎯 Social & Competitive

- **Global Leaderboard**: Ranked by total XP
- **Regional Leaderboards**: Compete with local students
- **Achievement System**: Unlock badges for milestones
- **Profile Display**: Showcase rank, XP, and equipment

---

## 🏗️ Architecture

### Design Pattern: MVVM (Model-View-ViewModel)

```
┌─────────────────────────────────────────────────────────────┐
│                    UI Layer (Jetpack Compose)                │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │  Login   │  │  Home    │  │ Video    │  │ Profile  │   │
│  │  Screen  │  │  Screen  │  │ Lesson   │  │ Screen   │   │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘   │
└─────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────┐
│                      ViewModel Layer                         │
│                  (EduVentureViewModel)                       │
│            State Management & Business Logic                 │
└─────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────┐
│                     Repository Layer                         │
│  ┌──────────────────┐  ┌────────────────┐  ┌─────────────┐ │
│  │  RPGRepository   │  │ DriveRouteSvc  │  │ DriveHelper │ │
│  │  (Student Data)  │  │ (Learning)     │  │ (PDF Fetch) │ │
│  └──────────────────┘  └────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              ↕
┌─────────────────────────────────────────────────────────────┐
│                        Data Layer                            │
│  ┌────────────┐  ┌────────────────┐  ┌──────────────────┐  │
│  │ Room DB    │  │ Google Drive   │  │ RunAnywhere AI   │  │
│  │ (Local)    │  │ API (Cloud)    │  │ SDK (On-Device)  │  │
│  └────────────┘  └────────────────┘  └──────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

### Key Design Principles

- **Separation of Concerns**: Clear boundaries between layers
- **Single Source of Truth**: Repository pattern for data management
- **Reactive UI**: StateFlow for real-time updates
- **Dependency Injection**: ViewModelFactory for initialization
- **Offline-First**: Local database with cloud sync

---

## 🛠️ Technology Stack

### Core Technologies
- **Kotlin** - Primary programming language
- **Jetpack Compose** - Modern declarative UI framework
- **Coroutines & Flow** - Asynchronous programming and reactive streams

### Android Jetpack

- **Room Database** - Local data persistence with type-safe SQL
- **ViewModel** - Lifecycle-aware state management
- **Navigation Compose** - Type-safe screen navigation
- **Lifecycle** - Android lifecycle integration

### External Services & APIs

- **Google Drive API** - Dynamic content delivery and updates
- **YouTube Android Player** - Embedded video lessons
- **RunAnywhere SDK** - On-device AI inference with LLaMA.cpp

### Media & Processing

- **Media3 ExoPlayer** - High-performance video playback
- **PDFBox Android** - PDF text extraction for AI context

### Networking

- **Retrofit** - Type-safe HTTP client
- **OkHttp** - HTTP/2 and connection pooling
- **Ktor** - Kotlin-first networking (SDK dependency)
- **Gson** - JSON serialization/deserialization

### AI/ML

- **LLaMA.cpp** - Quantized LLM inference optimized for mobile
- **RunAnywhere LLM Module** - ARM64-optimized AI models
- **SmolLM2 360M** - Fast, lightweight model (119 MB)
- **Qwen 2.5 0.5B** - Enhanced comprehension model (374 MB)



---

## 📂 Project Structure

```
app/src/main/java/com/runanywhere/startup_hackathon20/
├── data/
│   ├── database/
│   │   ├── AppDatabase.kt              # Room database configuration
│   │   ├── StudentDao.kt               # Data Access Object
│   │   └── Converters.kt               # Type converters for Room
│   ├── models/
│   │   ├── RPGModels.kt                # Knight, Route, Module, Badge models
│   │   ├── StudyMaterial.kt            # PDF and study content models
│   │   ├── TeacherModels.kt            # Teacher dashboard models
│   │   └── User.kt                     # User authentication models
│   └── repository/
│       ├── RPGRepository.kt            # Main student & route management
│       ├── DriveRouteService.kt        # Google Drive integration
│       ├── DriveHelper.kt              # PDF fetching from Drive
│       └── EduVentureRepository.kt     # General app repository
├── ui/
│   ├── screens/
│   │   ├── LoginScreen.kt              # Authentication UI
│   │   ├── HomeScreen.kt               # Main dashboard
│   │   ├── QuestScreen.kt              # Learning routes list
│   │   ├── VideoLessonScreen.kt        # Module video player
│   │   ├── AIChatScreen.kt             # AI tutor interface
│   │   ├── LeaderboardScreen.kt        # Ranking system
│   │   └── ProfileScreen.kt            # Student profile & settings
│   ├── navigation/
│   │   └── NavGraph.kt                 # Navigation configuration
│   └── theme/
│       ├── Color.kt                    # Color palette
│       ├── Theme.kt                    # Material Design theme
│       └── Type.kt                     # Typography system
├── utils/
│   └── SecurityUtils.kt                # Password hashing utilities
├── viewmodel/
│   ├── EduVentureViewModel.kt          # Main ViewModel
│   └── EduVentureViewModelFactory.kt   # ViewModel factory
├── MainActivity.kt                      # App entry point
└── MyApplication.kt                     # Application class
```


---

## 🎮 RPG System

### Rank Progression

| Rank       | XP Required | Defeated Enemies | Description           |
|------------|-------------|------------------|-----------------------|
| **Novice** | 0           | 0                | Starting rank         |
| **Squire** | 100         | 2-3              | Completed 2-3 modules |
| **Knight** | 300         | 5-7              | Mid-level learner     |
| **Hero**   | 600         | 10+              | Master of the subject |

### Weapon Progression

Weapons upgrade automatically based on defeated enemies:

- **Wooden Sword** - Starting weapon (0 enemies defeated)
- **Iron Sword** - Defeat 1 enemy
- **Golden Sword** - Defeat 3 enemies
- **Diamond Sword** - Defeat 5+ enemies

### XP Reward System

| Activity Type | XP Reward  | Example                |
|---------------|------------|------------------------|
| Easy Module   | 50 XP      | Module 1: Sets         |
| Medium Module | 75 XP      | Module 2: Relations    |
| Hard Module   | 100 XP     | Module 3: Trigonometry |
| Expert Module | 125-150 XP | Module 4-5             |
| Boss Module   | 200+ XP    | Final boss battles     |
| Achievement   | 50-200 XP  | Milestone bonuses      |

### Achievement System

Achievements unlock automatically:

| Achievement      | Requirement           | XP Bonus |
|------------------|-----------------------|----------|
| 🎯 First Steps   | Complete first quest  | 50 XP    |
| ⚔️ Quest Warrior | Complete 5 quests     | 100 XP   |
| 👑 Quest Master  | Complete 10 quests    | 200 XP   |
| 🏆 Perfect Score | Score 100% on a quiz  | 100 XP   |
| 🔥 Week Streak   | Maintain 7-day streak | 150 XP   |

### Enemy Types

Each module features a unique enemy:

- **The Necromancer of Sets** (Lvl 5) - Module 1
- **The Sorceress of Relations** (Lvl 10) - Module 2
- **The Triangle Titan** (Lvl 15) - Module 3
- **The Phantom of Imaginary Realm** (Lvl 20) - Module 4
- **The Parabola Dragon** (Lvl 25) - Module 5
- **The Demon Lord of Mathematics** (Lvl 30) - Final Boss

---

## 🤖 AI Integration

### RunAnywhere SDK Features

The app integrates the **RunAnywhere SDK** for on-device AI inference, ensuring privacy and offline
capability.

#### Available Models

| Model             | Size   | Speed    | Use Case                  |
|-------------------|--------|----------|---------------------------|
| SmolLM2 360M Q8_0 | 119 MB | Fast     | Quick answers, basic help |
| Qwen 2.5 0.5B     | 374 MB | Moderate | Better comprehension      |

#### AI Capabilities

- **Educational Q&A**: Answer questions about course content
- **Concept Explanations**: Break down complex topics
- **Study Material Analysis**: Read and discuss PDFs
- **Practice Problems**: Generate custom exercises
- **Flashcards**: Create study flashcards
- **Quiz Generation**: Generate practice quizzes
- **Study Tips**: Provide learning strategies


---

## 📹 Video Learning

### YouTube Integration

Each learning module includes an embedded YouTube video lesson:

```kotlin
QuestModule(
    videoUrl = "https://www.youtube.com/embed/VIDEO_ID",
    videoStartTime = 0,      // Start at 0 seconds
    videoEndTime = 1008,     // End at 16:48
)
```

### Features

- **Custom Time Segments**: Precise start/end times for focused learning
- **Embedded Player**: Native WebView integration
- **Fullscreen Support**: Enhanced viewing experience
- **Progress Tracking**: Auto-mark as complete when watched
- **Playback Controls**: Play, pause, seek, volume

### Implementation

The `VideoLessonScreen` uses a WebView with JavaScript enabled:

```kotlin
AndroidView(factory = { context ->
    WebView(context).apply {
        settings.javaScriptEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        loadUrl(module.videoUrl)
    }
})
```

---
### Type Converters

Custom converters handle complex types:

```kotlin
class Converters {
    @TypeConverter
    fun fromStringList(value: String): List<String> {
        return Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
    }
    
    @TypeConverter
    fun toStringList(list: List<String>): String {
        return Gson().toJson(list)
    }
}
```

---


### Demo Accounts

| Student ID | Password    | Knight Name        | Region        |
|------------|-------------|--------------------|---------------|
| STU001     | password123 | Arthur Pendragon   | North Kingdom |
| STU002     | password123 | Lancelot the Brave | East Empire   |
| STU003     | password123 | Guinevere          | West Realm    |

---




### Development Guidelines

- Write clean, readable code
- Follow MVVM architecture
- Use Kotlin conventions
- Comment complex logic
- Write unit tests
- Update documentation

### Areas We Need Help

- 🎨 UI/UX improvements
- 📝 Documentation and tutorials
- 🌍 Translations (i18n)
- 🧪 Testing and QA
- 🐛 Bug fixes
- ✨ New features





## 👥 Team

**Project Maintainers**: [@iamkazbrekker](https://github.com/iamkazbrekker), [@kambleom419-lab](https://github.com/kambleom419-lab)



---




**Built with ❤️ for the future of education**

*Making learning an adventure, one quest at a time!* 🎮📚

[⬆ Back to Top](#-eduventure-rpg---gamified-learning-platform)

</div>

