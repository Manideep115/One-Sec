package com.example.onesec

import android.accessibilityservice.AccessibilityService
import android.content.Context // Needed for SharedPreferences
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.example.onesec.BlockerState

class MyAccessibilityService : AccessibilityService() {

    private val TAG = "MyAccessibilityService"

    // We removed the hardcoded "distractingAppPackages" variable!
    // We will load it dynamically now.

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString() ?: return

            // Check if it's the popup itself (avoid infinite loop)
            val className = event.className?.toString() ?: ""
            if (packageName == "com.example.onesec" && className.contains("PopupActivity")) {
                return
            }

            // --- 1. LOAD BLOCKED APPS FROM STORAGE ---
            // We open the same "OneSecPrefs" file that MainActivity used
            val prefs = getSharedPreferences("OneSecPrefs", Context.MODE_PRIVATE)
            val blockedPackages = prefs.getStringSet("blocked_packages", emptySet()) ?: emptySet()

            // --- 2. CHECK IF CURRENT APP IS BLOCKED ---
            if (blockedPackages.contains(packageName)) {

                // It is in the user's block list!
                // Check if snooze timer is running.
                if (System.currentTimeMillis() < BlockerState.snoozeEndTime) {
                    Log.d(TAG, "Snooze is active for $packageName. Ignoring.")
                    return
                }

                // Timer is NOT running. Block it.
                Log.d(TAG, "Distracting app detected: $packageName. Launching popup!")
                launchPopup()

            } else {

                // Safe app opened. Reset timer.
                if (BlockerState.snoozeEndTime != 0L) {
                    Log.d(TAG, "Safe app opened. Resetting snooze timer.")
                    BlockerState.snoozeEndTime = 0L
                }
            }
        }
    }

    private fun launchPopup() {
        val intent = Intent(this, PopupActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

    override fun onInterrupt() {
        Log.d(TAG, "Accessibility Service Interrupted.")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "Accessibility Service Connected!")
    }
}