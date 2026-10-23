# MyTv 📺📱

**MyTv** is a modern video streaming application targeting both **Android TV** and **Mobile** (Phones & Tablets), built with 100% Kotlin, Jetpack Compose, Compose for TV Material3, Jetpack Media3 ExoPlayer, and a **Multi-Module Clean Architecture**.

---

## 🏛️ Architecture Overview

The project follows **Clean Architecture** principles and a **Target-Separated Multi-Module Layout**:

```text
MyTv (Root)
│
├── apps                             [App Assembly Layer]
│   ├── :mobile                      (Mobile App Shell & Entry)
│   └── :tv                          (Android TV App Shell & Entry)
│
├── feature                          [Presentation Layer - Target Separated]
│   ├── catalog
│   │   ├── :feature:catalog:mobile  (Mobile Compose Material3 Catalog UI)
│   │   └── :feature:catalog:tv      (TV Compose Material3 D-Pad Catalog UI)
│   └── player
│       └── :feature:player          (Shared Full-Screen Media3 Player UI)
│
└── core                             [Domain, Data & Infrastructure Layer]
    ├── :core:model                  (Domain Entities: VideoItem, Category)
    ├── :core:data                   (Data Layer: Repositories & Data Sources)
    ├── :core:player                 (Player Infrastructure: Media3 ExoPlayer Wrapper)
    └── :core:ui                     (UI Layer: Shared Design System & Theme Primitives)
```

### Module Responsibilities

- **`:apps:mobile` & `:apps:tv`**: App entry points (`MainActivity`) providing target navigation and dependency assembly.
- **`:feature:catalog:tv`**: 10-foot TV catalog UI using `androidx.tv.material3` with D-Pad focus zoom scaling and dynamic hero artwork.
- **`:feature:catalog:mobile`**: Touch-optimized catalog UI using `androidx.compose.material3`.
- **`:feature:player`**: Shared full-screen video playback UI backed by Media3.
- **`:core:model`**: Domain data models (`VideoItem`, `Category`).
- **`:core:data`**: Video repositories and streaming sample data (`SampleData`).
- **`:core:player`**: Media3 ExoPlayer instance manager (`PlayerWrapper`).
- **`:core:ui`**: Shared theme primitives and image loading configuration.

---

## 🛠️ Tech Stack & Dependencies

- **Language**: 100% [Kotlin](https://kotlinlang.org/)
- **UI Framework**:
  - [Jetpack Compose Material3](https://developer.android.com/jetpack/compose/designsystems/material3) (Mobile)
  - [Compose for TV Material3](https://developer.android.com/tv/compose) (Android TV)
- **Video Playback**: [Jetpack Media3 ExoPlayer](https://developer.android.com/guide/topics/media/media3)
- **Async Image Loading**: [Coil Compose](https://coil-kt.github.io/coil/)
- **Build System**: Gradle with Version Catalog (`gradle/libs.versions.toml`)

---

## ✨ Features

- **Android TV Experience**:
  - Dynamic background hero artwork updating instantly on D-Pad focus changes.
  - Categorized video rows with focus-driven card scaling (`CardDefaults.scale`).
  - Seamless D-Pad navigation.
- **Mobile Experience**:
  - Touch-friendly carousels and top navigation bar.
  - Featured video hero card.
- **Video Playback**:
  - Full-screen Media3 ExoPlayer supporting HLS and MP4 streaming.
  - Native playback controls and Back button gesture interceptor.

---

## 🚀 Building & Running

### Prerequisites
- **Android Studio** (Ladybug / 2024.2.1 or newer)
- **JDK 17 / 21**
- **Android SDK**: Compile SDK 37, Min SDK 26

### Gradle Commands

```bash
# Build debug APKs for all targets
./gradlew assembleDebug

# Install Mobile app on connected device/emulator
./gradlew :mobile:installDebug

# Install TV app on connected TV emulator/device
./gradlew :tv:installDebug
```

---

## 📄 License
This project is open source and available under the MIT License.
