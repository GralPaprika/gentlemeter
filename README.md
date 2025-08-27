# BarbarianCounter

BarbarianCounter is a fun, personal Android application designed to track "ungentlemanly" behavior. The core idea is to gamify the journey towards embracing classic gentlemanly conduct.

**How it Works:**

*   **Count Infractions:** The app allows you to increment a counter each time an "ungentlemanly" action is observed or performed.
*   **Reach the Milestone:** Every 10 infractions, the counter automatically resets.
*   **Log Achievements:** When the counter resets at the 10th mark, the app registers this milestone. This signifies that the person tracking their behavior has reached a point where they are due for a "reward" (as defined by the user).
*   **Gamified Improvement:** The ultimate goal is to encourage more thoughtful and considerate behavior by making the process of self-improvement engaging and rewarding.

**Purpose:**

This app is a personal tool for self-reflection and lighthearted motivation. It aims to bring a playful approach to cultivating "old-school gentleman" values.

**Features (Conceptual - to be implemented/refined):**

*   Simple interface for incrementing the counter.
*   Clear visual indication of the current count.
*   Automatic reset of the counter at 10 infractions.
*   A log or history of when the 10th mark milestones were achieved.
*   (Potential Future Feature) Customizable rewards or notifications when a milestone is reached.
*   (Potential Future Feature) Theming options (Light/Dark mode supported, as seen in `Color.kt`).

**Tech Stack (based on provided dependencies):**

*   **UI:** Jetpack Compose (androidx.compose)
*   **Architecture:** Likely MVVM with Jetpack ViewModel (androidx.lifecycle)
*   **Navigation:** Jetpack Navigation Compose (androidx.navigation)
*   **Dependency Injection:** Hilt (com.google.dagger:hilt-android)
*   **Asynchronous Operations:** Kotlin Coroutines (org.jetbrains.kotlinx:kotlinx-coroutines-android)
*   **Data Persistence (Potentially):** Room (androidx.room) for storing milestone data locally.
*   **Networking (Potentially, if features expand):** Retrofit & OkHttp (com.squareup.retrofit2, com.squareup.okhttp3)

**Project Status:**

This is a personal project currently under development.

**How to Use (Once Released/If Shared):**

1.  Install the Android application.
2.  Open the app.
3.  Tap the designated button or area to increment the "ungentlemanly" action counter.
4.  Observe the counter. When it reaches 10, it will reset, and the milestone will be logged.
5.  Reflect on your progress and claim your self-defined "reward"!

**Disclaimer:**

This app is intended for personal entertainment and self-improvement. The definition of "ungentlemanly" is subjective and up to the individual user.
