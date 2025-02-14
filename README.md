# Newsy

A modern Android news application built with Jetpack Compose that delivers up-to-date news articles across various categories with offline support.

## Features

- **Hot News Carousel**: Interactive news slider showcasing the latest headlines
- **Category-based News**: Browse news by categories:
  - Business
  - Entertainment
  - Health
  - Science
  - Sports
  - Technology
- **Offline Support**: Powered by Room database for seamless offline reading
- **Article Search**: Discover articles with powerful search functionality
- **Bookmarks**: Save your favorite articles for later reading
- **Country Selection**: Customize news feed based on country preference (currently US)
- **Clean UI**: Material Design 3 implementation with modern Android practices

## Screenshots

| Home | Detail | Explore | Favorites | Drawer | Settings |
|------|--------|---------|-----------|---------|-----------|
|![Home Screen](/screenshots/home.jpeg)|![Detail Screen](/screenshots/detail.jpeg)|![Explore Screen](/screenshots/explore.jpeg)|![Favorites Screen](/screenshots/favorites.jpeg)|![Drawer](/screenshots/drawer.jpeg)|![Settings Screen](/screenshots/settings.jpeg)|

## Technology Stack

### Architecture
- Clean Architecture
- MVI (Model-View-Intent) pattern

### Core Technologies
- **UI Framework**: Jetpack Compose with Material 3
- **Programming Language**: Kotlin 2.0+
- **Local Storage**: Room Database
- **Pagination**: Paging 3
- **Navigation**: Compose Navigation
- **State Management**: MVI pattern

### Key Libraries
- Jetpack Compose Pager
- Room Database
- Paging 3
- Material 3 Components
- Navigation Compose

## Features in Detail

### Home Screen
- Hot News carousel using Compose Pager
- Category selection with horizontal scrollable tabs
- Paginated article list with Paging 3
- Navigation drawer for app sections

### Article Details
- Comprehensive article view
- Direct link to source website
- Bookmark functionality

### Discover
- Search functionality for articles
- Real-time search results
- Category-based filtering

### Offline Support
- Room database integration
- Automatic caching of articles
- Seamless offline-online transitions

### Settings
- Country preference selection
- Future-ready for additional countries

## Installation

1. Clone this repository
2. Open project in Android Studio
3. Build and run on an emulator or physical device

## Requirements

- Android Studio Ladybug or newer
- Minimum SDK: 25
- Target SDK: 35
- Kotlin 2.0 or newer
