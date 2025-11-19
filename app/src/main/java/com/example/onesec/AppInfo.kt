package com.example.onesec

import android.graphics.drawable.Drawable

/**
 * This data class represents one app on the user's phone.
 * We will use this to populate the list in the UI.
 */
data class AppInfo(
    val appName: String,
    val packageName: String,
    val icon: Drawable,
    var isSelected: Boolean = false // We'll use this for the checkbox state
)