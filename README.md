# GlassMedia

**GlassMedia** is a native Android media organization app designed to help users clean up their storage using intuitive swipe gestures. The application follows a "Professional Glassmorphism" design philosophy, featuring high polish, transparency, background blurs, and fluid animations.

## Project Goal
To build a Minimum Viable Product (MVP) that allows users to review their media (images and videos) and organize them efficiently:
- **Swipe Right:** Keep.
- **Swipe Left:** Delete (Move to Trash).
- **Swipe Down:** Skip.

## Architecture
The project follows **MVVM with Clean Architecture principles** and is modularized to separate concerns effectively.

### Modules
*   **:app** - The application entry point, wiring everything together.
*   **:core** - Common utilities, shared data models (`MediaType`, `ReviewedState`), and extensions.
*   **:data** - The data layer containing the Room Database, Entities (`MediaItem`, `VirtualFolder`, `TrashItem`, `Settings`), and DAOs.
*   **:ui** - Jetpack Compose UI screens, the Glassmorphism theme, and reusable components.
*   **:media-scanner** - Logic for querying `MediaStore` and populating the local database.
*   **:actions** - Business logic for file operations (Delete, Move, Undo) and Trash management.

## Technical Stack
*   **Language:** Kotlin
*   **UI:** Jetpack Compose (Material 3)
*   **Architecture:** MVVM, Modularization
*   **Dependency Injection:** Hilt
*   **Local Storage:** Room Database
*   **Async:** Coroutines & Flow
*   **Image Loading:** Coil
*   **Video:** ExoPlayer
*   **Background Tasks:** WorkManager
*   **Navigation:** Navigation Compose

## Current State
The project infrastructure has been initialized with the following components:

1.  **Project Structure:** Multi-module Gradle setup is complete with dependencies configured for all modules.
2.  **Data Layer:**
    *   **Room Database:** `AppDatabase` is set up with Hilt injection.
    *   **Entities:**
        *   `MediaItem`: Represents a media file with its review state.
        *   `VirtualFolder`: Represents a collection of media based on filters.
        *   `TrashItem`: Tracks deleted items for the recycle bin feature.
        *   `Settings`: User preferences for trash retention and sorting.
    *   **DAOs:** Interfaces for data access are implemented.
    *   **TypeConverters:** Converters for Enums (`MediaType`, `ReviewedState`, etc.) are in place.

## Getting Started
1.  Open the project in Android Studio (Iguana or later recommended).
2.  Sync Gradle project.
3.  The project currently compiles and contains the core data foundation. UI and logic implementation will follow in subsequent updates.
