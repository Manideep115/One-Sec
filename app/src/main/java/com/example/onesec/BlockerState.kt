package com.example.onesec

/**
 * This is a simple singleton "object" that will hold the
 * state of our app. It lets the Activity and Service
 * easily share one piece of data.
 */
object BlockerState {
    /**
     * This will store the exact time (in milliseconds)
     * when the "snooze" period ends.
     * It's set to 0 by default, meaning no snooze is active.
     */
    var snoozeEndTime: Long = 0L
}