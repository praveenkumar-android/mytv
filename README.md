# MyTv 📺📱

**MyTv** is a modern video streaming application targeting both **Android TV** and **Mobile** (Phones & Tablets), built with 100% Kotlin, Jetpack Compose, Compose for TV Material3, Jetpack Media3 ExoPlayer, and a modular clean architecture.

---

## 🏛️ Architecture Overview

The project follows **Clean Architecture** principles with a **multi-module, target-separated layout**:

```text
MyTv (Root)
├── mobile                         # Mobile app entry point
├── tv                             # Android TV app entry point
├── core
│   ├── model                      # Domain models (VideoItem, Category)
│   ├── data                       # Repositories and data sources
│   ├── player                     # Media3 / ExoPlayer infrastructure
│   └── ui                         # Shared theme and UI primitives
├── feature
│   ├── catalog:mobile             # Mobile catalog UI
│   ├── catalog:tv                 # TV catalog UI
│   └── player                     # Shared full-screen playback screen
├── build.gradle.kts
├── settings.gradle.kts
├── gradle/libs.versions.toml
├── gradlew
├── gradlew.bat
└── README.md
```

### Module Responsibilities

- **`:mobile`**: Mobile app entry point and UI assembly.
- **`:tv`**: Android TV app entry point and UI assembly.
- **`:feature:catalog:mobile`**: Touch-friendly catalog UI built with Compose Material3.
- **`:feature:catalog:tv`**: D-pad optimized TV UI built with Compose for TV Material3.
- **`:feature:player`**: Shared full-screen media playback UI powered by Media3.
- **`:core:model`**: Domain models such as `VideoItem` and `Category`.
- **`:core:data`**: Repositories and sample media data.
- **`:core:player`**: ExoPlayer wrapper logic and playback infrastructure.
- **`:core:ui`**: Shared design system, theme primitives, and image loading components.

---

## 🛠️ Tech Stack & Dependencies

- **Language**: 100% [Kotlin](https://kotlinlang.org/)
- **UI Framework**:
  - [Jetpack Compose Material3](https://developer.android.com/jetpack/compose/designsystems/material3) for mobile
  - [Compose for TV Material3](https://developer.android.com/tv/compose) for Android TV
- **Video Playback**: [Jetpack Media3 ExoPlayer](https://developer.android.com/guide/topics/media/media3)
- **Async Image Loading**: [Coil Compose](https://coil-kt.github.io/coil/)
- **Build System**: Gradle with version catalog (`gradle/libs.versions.toml`)

---

## ✨ Features

- **Android TV Experience**:
  - Dynamic hero artwork updates while navigating with a D-pad
  - Focus-driven card scaling and cinematic layout
  - Smooth, TV-first navigation
- **Mobile Experience**:
  - Touch-friendly carousels and top navigation
  - Feature hero card for browsing content
- **Video Playback**:
  - Full-screen media playback using Media3
  - Support for streaming content, including HLS and MP4 sources
  - Native playback controls and back-navigation handling

---

## 🚀 Building & Running

### Prerequisites
- Android Studio (latest stable version recommended)
- JDK 17 or newer
- Android SDK configured for the project

### Gradle Commands

```bash
# Build debug APKs for all targets
./gradlew assembleDebug

# Install the mobile app on a connected device/emulator
./gradlew :mobile:installDebug

# Install the TV app on a connected emulator/device
./gradlew :tv:installDebug
```

---

## ℹ️ Notes

This project is primarily a modular Android media UI sample and demonstration app. It showcases modern Kotlin, Compose, and Media3 patterns in a clean multi-module architecture, and is intended for learning, UI experimentation, and app architecture exploration rather than as a production backend-connected streaming platform.

---

## 📄 License

This project is open source and available under the MIT License.
