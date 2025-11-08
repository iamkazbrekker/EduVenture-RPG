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
- [Google Drive Setup](#-google-drive-setup)
- [Project Structure](#-project-structure)
- [RPG System](#-rpg-system)
- [AI Integration](#-ai-integration)
- [Video Learning](#-video-learning)
- [Database Schema](#-database-schema)
- [Configuration](#-configuration)
- [Development](#-development)
- [Troubleshooting](#-troubleshooting)
- [Roadmap](#-roadmap)
- [Contributing](#-contributing)
- [License](#-license)

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

## 🚀 Getting Started

### Prerequisites

- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: 17 or higher
- **Android SDK**: API 24+ (Android 7.0 Nougat or higher)
- **Storage**: ~500 MB free for app and AI models
- **RAM**: 4GB+ recommended for AI features

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/eduventure-rpg.git
   cd eduventure-rpg
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Sync Gradle dependencies**
   ```bash
   ./gradlew build
   ```
   Or use Android Studio's "Sync Project with Gradle Files" button

4. **Configure API Keys** (Optional for testing)
   - The app includes demo API keys
   - For production, move to `local.properties`

5. **Run the app**
   - Connect an Android device (recommended) or start an emulator
   - Click "Run" (Shift+F10) in Android Studio

### First Launch Quick Start

1. **Login Screen**
   - Use demo account: `STU001` / `password123`
   - Or create a new knight account with "Register"

2. **Download AI Model** (Optional but recommended)
   - Navigate to AI Chat screen
   - Tap "AI Model" button
   - Select "SmolLM2 360M Q8_0" (119 MB - fastest)
   - Wait for download and tap "Load"

3. **Start Your Quest**
   - Select "The Path of Numbers" (Mathematics route)
   - Watch video lesson for Module 1: Sets
   - Complete the module to earn 50 XP
   - Defeat "The Necromancer of Sets"!

---

## 📁 Google Drive Setup

### Overview

Learning routes are dynamically fetched from Google Drive, allowing educators to update content
without app recompilation. This enables real-time curriculum updates and A/B testing of content.

### Current Configuration

**Folder ID**: `1sFgl7wGTuAGWRsQi-sNYW4Me-VrKmD9J`  
**API Key**: `AIzaSyCztwFMxHV3fP1Zd4j1XhpyZF6DkgCIK30`

⚠️ **Security Note**: For production deployments, move API keys to `local.properties` or use
Firebase Remote Config for better security.

### Setting Up Your Own Drive Folder

#### Step 1: Create and Configure Folder

1. Create a new folder in Google Drive
2. Right-click → **Share**
3. Click "Restricted" → Select **"Anyone with the link"**
4. Set permission to **"Viewer"**
5. Click "Copy link" → "Done"

#### Step 2: Extract Folder ID

From the copied link:

```
https://drive.google.com/drive/folders/1sFgl7wGTuAGWRsQi-sNYW4Me-VrKmD9J
                                          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                                          This is your Folder ID
```

#### Step 3: Create Learning Route JSON

Create a file named `math_route.json` with this structure:

```json
{
  "id": "math_route",
  "subject": "Mathematics",
  "routeName": "The Path of Numbers",
  "description": "Journey through the mystical realm of mathematics!",
  "backgroundTheme": "MYSTIC",
  "finalBoss": "The Demon Lord of Mathematics",
  "modules": [
    {
      "id": "math_module_1",
      "moduleNumber": 1,
      "title": "Module 1: Sets",
      "topic": "Sets - The Foundation",
      "enemyName": "The Necromancer of Sets",
      "enemyDescription": "The Necromancer has captured villagers in his mysterious collections. Learn Sets to save them!",
      "enemyLevel": 5,
      "xpReward": 50,
      "videoUrl": "https://www.youtube.com/embed/jKUpw3TyjHI",
      "videoStartTime": 0,
      "videoEndTime": 1008,
      "isBoss": false
    }
  ]
}
```

#### Step 4: Upload to Drive

1. Upload `math_route.json` to your Drive folder
2. Right-click the file → **Share**
3. Set to **"Anyone with the link"** → **"Viewer"**

#### Step 5: Update App Configuration

**File**:
`app/src/main/java/com/runanywhere/startup_hackathon20/data/repository/DriveRouteService.kt`

```kotlin
companion object {
    const val API_KEY = "YOUR_API_KEY_HERE"
    const val DRIVE_FOLDER_ID = "YOUR_FOLDER_ID_HERE"
}
```

### Available Themes

- `FOREST` - Lush forest environment
- `MOUNTAIN` - Rocky mountain terrain
- `DESERT` - Sandy desert landscape
- `CASTLE` - Medieval castle setting
- `DUNGEON` - Underground dungeon
- `MYSTIC` - Magical/mystical environment

### Testing Drive Connection

```bash
# Test API access (replace with your credentials)
curl "https://www.googleapis.com/drive/v3/files?q='FOLDER_ID'+in+parents&key=API_KEY"
```

### Manual Refresh

In the app:

1. Navigate to Home screen
2. Settings/Options menu
3. Tap "Refresh Routes from Drive"

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

### Key Files Explained

#### `RPGRepository.kt`

Central repository managing:

- Student profiles and authentication
- Knight progression (XP, levels, ranks)
- Learning route loading and caching
- Google Drive synchronization
- Leaderboard queries
- Achievement unlocking

**Key Methods:**
- `initializeDatabase()` - Fetches routes from Drive on startup
- `login(studentId, password)` - Authenticates users
- `register(...)` - Creates new knight accounts
- `completeModule(moduleId)` - Awards XP and updates progress
- `refreshRoutesFromDrive()` - Manually syncs with Drive

#### `DriveRouteService.kt`

Handles Google Drive API integration:

- JSON route file discovery and download
- Route parsing and validation
- Cache management
- Error handling and fallback

#### `EduVentureViewModel.kt`

State management and business logic:

- UI state flows (knightProfile, learningRoutes, etc.)
- AI chat message handling
- Model download progress tracking
- Navigation state management

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

#### Usage Example

```kotlin
// In the app
viewModel.sendChatMessage("Explain quadratic functions in simple terms")

// AI responds with streaming text:
// "A quadratic function is a polynomial of degree 2..."
```

#### Implementation Details

The ViewModel handles AI interactions:

```kotlin
fun sendChatMessage(message: String) {
    viewModelScope.launch {
        val response = RunAnywhereManager.generateStream(
            prompt = message,
            systemPrompt = "You are an educational AI assistant..."
        )
        // Stream tokens to UI
    }
}
```

### Privacy & Performance

- ✅ **100% On-Device**: All inference runs locally
- ✅ **No Data Sent**: Nothing leaves your device
- ✅ **Offline Capable**: Works without internet (after model download)
- ✅ **Optimized**: ARM64 NEON acceleration for fast inference
- ⚠️ **Performance**: 2-10 tokens/sec depending on device

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

## 🗄️ Database Schema

### Room Database Tables

#### Knight Profiles

```sql
CREATE TABLE knight_profiles (
    id TEXT PRIMARY KEY,
    studentId TEXT UNIQUE NOT NULL,
    passwordHash TEXT NOT NULL,
    knightName TEXT NOT NULL,
    rank TEXT NOT NULL,                    -- NOVICE, SQUIRE, KNIGHT, HERO
    totalXP INTEGER NOT NULL,
    currentHP INTEGER NOT NULL,
    maxHP INTEGER NOT NULL,
    defeatedEnemies TEXT NOT NULL,         -- JSON array
    unlockedRoutes TEXT NOT NULL,          -- JSON array
    badges TEXT NOT NULL,                  -- JSON array
    equippedWeapon TEXT DEFAULT 'Wooden Sword',
    equippedArmor TEXT DEFAULT 'Cloth Armor',
    email TEXT,
    phoneNumber TEXT,
    region TEXT
)
```

#### Learning Routes

```sql
CREATE TABLE learning_routes (
    id TEXT PRIMARY KEY,
    subject TEXT NOT NULL,
    routeName TEXT NOT NULL,
    description TEXT,
    backgroundTheme TEXT,                  -- FOREST, MOUNTAIN, etc.
    modules TEXT NOT NULL,                 -- JSON array of QuestModule
    finalBoss TEXT,
    isUnlocked INTEGER DEFAULT 1
)
```

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

## ⚙️ Configuration

### Build Configuration

**File**: `app/build.gradle.kts`

```kotlin
android {
    compileSdk = 36
    
    defaultConfig {
        applicationId = "com.runanywhere.startup_hackathon20"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
    
    buildFeatures {
        compose = true
        buildConfig = true
    }
}
```

### API Keys (for production)

**File**: `local.properties` (gitignored)

```properties
GOOGLE_DRIVE_API_KEY=your_api_key_here
GOOGLE_DRIVE_FOLDER_ID=your_folder_id_here
```

Access in code via `BuildConfig`:

```kotlin
// In build.gradle.kts
android {
    defaultConfig {
        buildConfigField("String", "DRIVE_API_KEY", 
            "\"${project.properties["GOOGLE_DRIVE_API_KEY"]}\"")
    }
}

// In code
val apiKey = BuildConfig.DRIVE_API_KEY
```

### Demo Accounts

| Student ID | Password    | Knight Name        | Region        |
|------------|-------------|--------------------|---------------|
| STU001     | password123 | Arthur Pendragon   | North Kingdom |
| STU002     | password123 | Lancelot the Brave | East Empire   |
| STU003     | password123 | Guinevere          | West Realm    |

---

## 💻 Development

### Building from Source

```bash
# Clone repository
git clone https://github.com/yourusername/eduventure-rpg.git
cd eduventure-rpg

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug

# Run all tests
./gradlew test
```

### Code Style

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable names
- Comment complex logic
- Keep functions small and focused
- Write unit tests for business logic

### Adding a New Learning Route

1. **Create JSON file** in Google Drive:
   ```json
   {
     "id": "physics_route",
     "subject": "Physics",
     "routeName": "The Quest for Motion",
     "modules": [...]
   }
   ```

2. **Upload to Drive folder** and make it public

3. **Refresh in app** via Settings → Refresh Routes

4. Route appears automatically in Quest screen!

### Adding a New Screen

1. Create screen composable in `ui/screens/`
2. Add route to `NavGraph.kt`
3. Add navigation call from parent screen
4. Update ViewModel if state management needed

---

## 🐛 Troubleshooting

### Common Issues

#### 1. Drive Routes Not Loading

**Symptoms**: Empty quest list, fallback route only

**Solutions**:

- Check internet connection
- Verify folder permissions (Anyone with link → Viewer)
- Verify JSON files are public
- Check Logcat for Drive API errors
- Try manual refresh in app

**Debug**:

```bash
# Test folder access
curl "https://www.googleapis.com/drive/v3/files?q='FOLDER_ID'+in+parents&key=API_KEY"
```

#### 2. AI Model Won't Load

**Symptoms**: "No model loaded", send button disabled

**Solutions**:

- Ensure sufficient storage (~500 MB free)
- Check internet for download
- Wait for download to complete (check progress)
- Tap "Load" after download
- Restart app if stuck

#### 3. Video Not Playing

**Symptoms**: Black screen, playback error

**Solutions**:

- Check internet connection
- Verify YouTube URL format: `https://www.youtube.com/embed/VIDEO_ID`
- Enable JavaScript in WebView (should be default)
- Try different video

#### 4. Login Fails

**Symptoms**: "Invalid credentials" error

**Solutions**:

- Use demo account: `STU001` / `password123`
- Check for typos in studentId
- Password is case-sensitive
- Try registering a new account

#### 5. App Crashes

**Symptoms**: App closes unexpectedly

**Solutions**:

- Check device RAM (need 4GB+)
- Clear app cache
- Reinstall app
- Check Logcat for stack trace
- Report issue with logs

### Checking Logs

In Android Studio:

1. Open **Logcat** (View → Tool Windows → Logcat)
2. Filter by tag:
   - `RPGRepository` - Database and Drive operations
   - `DriveRouteService` - Drive API calls
   - `EduVentureViewModel` - State management
   - `RunAnywhere` - AI operations

### Getting Help

- **Documentation**: Check this README and code comments
- **Issues**: [GitHub Issues](https://github.com/yourusername/eduventure-rpg/issues)
- **Discussions**: [GitHub Discussions](https://github.com/yourusername/eduventure-rpg/discussions)

---

## 🗺️ Roadmap

### Phase 1: Core Features ✅ (Complete)

- [x] RPG gamification system
- [x] Google Drive content integration
- [x] AI tutoring with RunAnywhere SDK
- [x] Video lesson player
- [x] Leaderboard system
- [x] Student profiles and authentication

### Phase 2: Teacher Features 🔄 (In Progress)

- [ ] Teacher dashboard
- [ ] Student analytics and reporting
- [ ] Custom quest creation UI
- [ ] Assignment system
- [ ] Grade tracking
- [ ] Parent portal

### Phase 3: Enhanced Learning 📋 (Planned)

- [ ] Interactive quizzes with instant feedback
- [ ] Flashcard study mode
- [ ] Spaced repetition system
- [ ] Peer-to-peer challenges
- [ ] Study groups and collaboration
- [ ] Offline mode improvements

### Phase 4: Advanced Features 🚀 (Future)

- [ ] Multi-language support (i18n)
- [ ] Voice-to-text for AI chat
- [ ] AR learning experiences
- [ ] Virtual classroom integration
- [ ] Advanced analytics dashboard
- [ ] iOS app (SwiftUI)
- [ ] Web app (React/Next.js)

---

## 🤝 Contributing

We welcome contributions! Here's how to get started:

### How to Contribute

1. **Fork the repository**
   ```bash
   git clone https://github.com/yourusername/eduventure-rpg.git
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```

3. **Make your changes**
   - Follow code style guidelines
   - Add tests for new features
   - Update documentation

4. **Commit your changes**
   ```bash
   git commit -m "Add amazing feature"
   ```

5. **Push to your fork**
   ```bash
   git push origin feature/amazing-feature
   ```

6. **Open a Pull Request**
   - Describe your changes
   - Link related issues
   - Add screenshots if UI changes

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

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024 EduVenture RPG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
```

---

## 🙏 Acknowledgments

### Open Source Libraries

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern UI toolkit
- [RunAnywhere SDK](https://github.com/RunanywhereAI) - On-device AI inference
- [Room](https://developer.android.com/training/data-storage/room) - Database library
- [Retrofit](https://square.github.io/retrofit/) - HTTP client
- [Gson](https://github.com/google/gson) - JSON parsing
- [PDFBox Android](https://github.com/TomRoush/PdfBox-Android) - PDF processing

### APIs & Services

- **Google Drive API** - Cloud storage and content delivery
- **YouTube Player API** - Video integration
- **LLaMA.cpp** - Efficient LLM inference

### Inspiration

- [Duolingo](https://www.duolingo.com/) - Gamification in education
- [Khan Academy](https://www.khanacademy.org/) - Free quality education
- [Habitica](https://habitica.com/) - RPG for habit building

---

## 👥 Team

**Project Maintainer**: [@yourusername](https://github.com/yourusername)

**Contributors**: See [CONTRIBUTORS.md](CONTRIBUTORS.md)

---

## 📞 Contact & Support

- **Email**: support@eduventurerpg.com
- **Website**: [eduventurerpg.com](https://eduventurerpg.com)
- **GitHub
  **: [github.com/yourusername/eduventure-rpg](https://github.com/yourusername/eduventure-rpg)
- **Discord**: [Join our community](https://discord.gg/eduventurerpg)
- **Twitter**: [@EduVentureRPG](https://twitter.com/EduVentureRPG)

---

## 📊 Project Stats

![GitHub stars](https://img.shields.io/github/stars/yourusername/eduventure-rpg?style=social)
![GitHub forks](https://img.shields.io/github/forks/yourusername/eduventure-rpg?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/yourusername/eduventure-rpg?style=social)
![GitHub issues](https://img.shields.io/github/issues/yourusername/eduventure-rpg)
![GitHub pull requests](https://img.shields.io/github/issues-pr/yourusername/eduventure-rpg)
![GitHub last commit](https://img.shields.io/github/last-commit/yourusername/eduventure-rpg)

---

<div align="center">

**Built with ❤️ for the future of education**

*Making learning an adventure, one quest at a time!* 🎮📚

[⬆ Back to Top](#-eduventure-rpg---gamified-learning-platform)

</div>

