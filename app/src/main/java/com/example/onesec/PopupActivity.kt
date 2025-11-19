package com.example.onesec

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.onesec.BlockerState // We're still importing our "memory"

class PopupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)

        // --- We no longer need the "getStringExtra" line ---

        val proceedButton: Button = findViewById(R.id.buttonProceed)
        val cancelButton: Button = findViewById(R.id.buttonCancel)

        // Button 1: "Yes, I have a purpose"
        proceedButton.setOnClickListener {

            // --- THIS IS THE NEW TIMER LOGIC ---
            // Set the snooze end time to 10 minutes from now.
            // (10 minutes * 60 seconds * 1000 milliseconds)
            BlockerState.snoozeEndTime = System.currentTimeMillis() + (10 * 60 * 1000)

            // Then, close the popup as normal
            finish()
        }

        // Button 2: "No, just browsing"
        cancelButton.setOnClickListener {
            // Send user to the home screen
            val homeIntent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(homeIntent)

            // Close this popup activity
            finish()
        }
    }

    // Optional: If the user presses the "back" button,
    // treat it the same as "No, just browsing".
    override fun onBackPressed() {
        super.onBackPressed()
        val homeIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(homeIntent)
    }
}