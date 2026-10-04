# NewsOutletApp 🗞️

A South African news app built with Jetpack Compose and Clean Architecture, delivering the latest headlines, instant search, and offline-ready bookmarks — all in one place.

---

## 📱 Screenshots

### NewsApp Screens
![Action](app/src/screenshots/screen1.png)
![Action](app/src/screenshots/screen2.png)
![Action](app/src/screenshots/screen3.png)
![Action](app/src/screenshots/screen4.jpeg)
![Action](app/src/screenshots/screen8.jpeg)
![Action](app/src/screenshots/screen9.jpg)

---

## ✨ Features

- 🇿🇦 Curated South African news feed, powered by the NewsData.io API
- 🔍 Debounced live search across thousands of articles
- 🔖 Bookmark articles to read later, backed by a local Room database
- 📰 Full article details view with image, source, and publish date
- 🔗 "Read full article" opens the original source in a Chrome Custom Tab
- 📤 Share articles directly from the details screen
- ♾️ Infinite scroll via Jetpack Paging 3
- 🧭 Dual navigation — bottom nav bar and a hamburger drawer
- 🌗 Light/dark theme support
- 🎬 Persistent onboarding flow, shown only on first launch
- ⚠️ Graceful error handling with retry, for both feed loading and network failures

---

## 🛠️ Tech Stack

- **Language** — Kotlin
- **UI** — Jetpack Compose, Material 3
- **Architecture** — Clean Architecture (Presentation / Domain / Data) + MVVM
- **State Management** — StateFlow, sealed UI states, unidirectional data flow
- **Dependency Injection** — Hilt
- **Networking** — Retrofit, OkHttp, Gson
- **Pagination** — Jetpack Paging 3
- **Local Persistence** — Room (bookmarks + offline feed caching), Jetpack DataStore (onboarding state)
- **Navigation** — Jetpack Navigation Compose
- **Image Loading** — Coil 3
- **In-App Browsing** — AndroidX Browser (Chrome Custom Tabs)
- **CI/CD** — GitHub Actions (build + lint on every PR)

---

## 🏗️ Architecture

The app follows Clean Architecture with strict layer boundaries:

- **Domain** — pure Kotlin models, repository interfaces, and use cases. No Android or framework dependencies.
- **Data** — Retrofit API definitions, DTOs, Room entities/DAOs, and repository implementations that map data into domain models.
- **Presentation** — Jetpack Compose screens and ViewModels, each screen split into a stateful entry point and a stateless, previewable `*Content` composable.

Dependencies always point inward — Presentation and Data both depend on Domain, but never on each other directly.

---

## 🤖 How It Works

- The feed is backed by **Room** as the single source of truth, kept in sync with the **NewsData.io** API via a `RemoteMediator`, so previously loaded articles remain available offline.
- Search queries the API live, paginated with **Paging 3**, debounced to avoid firing a request on every keystroke.
- Bookmarked articles persist locally in Room, any screen reading bookmarks reflects changes instantly, with no manual refresh.
- Onboarding completion is tracked via **DataStore**, so returning users skip straight to the main app.
- Tapping an article shares its data through a lightweight in-memory holder and opens the **Details** screen, where users can bookmark, share, or read the full article via a Chrome Custom Tab.

---

## 🚧 Roadmap

- [ ] Pull-to-refresh
- [ ] Unit tests (ViewModels, use cases, repositories)
- [ ] Crash reporting integration