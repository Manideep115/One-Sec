One Sec - Digital Wellness App 🧠

One Sec is a native Android application designed to help users break unconscious scrolling habits. By introducing a mandatory "1-second" pause before opening distracting apps, it encourages mindfulness and reduces screen time.

📱 Features

Custom App Blocking: Select exactly which apps you want to intercept (e.g., Instagram, Twitter, TikTok) from a searchable list of all installed applications.

The "One Sec" Interceptor: A full-screen popup appears instantly over blocked apps, asking if you really need to open them.

Smart Session Timer:

If you choose "Yes, I have a purpose," you get a 10-minute session to use the app freely.

If you leave the app (e.g., go to the Home screen), the timer resets immediately. The next time you open the app, you will be blocked again.

Privacy First: No data leaves your device. All processing happens locally using Android's Accessibility Services.

🛠️ Tech Stack

Language: Kotlin

Core API: AccessibilityService (to detect app launches)

UI: XML Layouts, RecyclerView, SearchView

Concurrency: Kotlin Coroutines (for efficient app fetching)

Storage: SharedPreferences (to persist your block list)

🚀 Getting Started

Prerequisites

Android Studio Iguana or later (recommended)

Android Device/Emulator running Android 7.0 (API 24) or higher

Installation

Clone the repository:

git clone [https://github.com/YourUsername/One-Sec-App.git](https://github.com/YourUsername/One-Sec-App.git)


Open in Android Studio:

Select "Open an Existing Project" and navigate to the cloned folder.

Wait for Gradle to sync.

Build and Run:

Connect your device via USB or start an emulator.

Click the green Run button (▶).

⚠️ Important Setup Step

Because this app uses the Accessibility Service to monitor other apps, it requires a special permission that cannot be granted automatically.

Open the app on your phone.

Click the "Enable 1 Sec Service" button.

You will be taken to your phone's Accessibility Settings.

Find "One Sec" (usually under "Installed Apps" or "Downloaded Services").

Toggle the switch to ON and allow the permission.

Go back to the app and select the apps you want to block!

📂 Project Structure

MainActivity.kt: The main UI. Handles fetching the installed app list, the search bar logic, and saving user preferences.

MyAccessibilityService.kt: The "engine" of the app. It runs in the background, detects window state changes, and launches the popup if a blocked app is detected.

PopupActivity.kt: The interceptor screen. Handles the "Yes/No" logic and manages the 10-minute session timer.

BlockerState.kt: A singleton object that acts as the shared memory between the Service and the Activity to track the session timer.

🔒 Permissions

This app requires high-level permissions to function:

BIND_ACCESSIBILITY_SERVICE: To detect when other apps are opened.

SYSTEM_ALERT_WINDOW: To draw the popup overlay on top of other apps.

QUERY_ALL_PACKAGES: To display the list of installed apps for the user to select.

🤝 Contributing

Contributions are welcome!

Fork the project.

Create your feature branch (git checkout -b feature/AmazingFeature).

Commit your changes (git commit -m 'Add some AmazingFeature').

Push to the branch (git push origin feature/AmazingFeature).

Open a Pull Request.

