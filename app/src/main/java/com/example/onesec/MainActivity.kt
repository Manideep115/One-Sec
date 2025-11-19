package com.example.onesec

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView // Import SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: AppAdapter

    // We keep two lists:
    // 1. The full list of all installed apps
    // 2. The filtered list currently being shown
    private var allAppsList: List<AppInfo> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences (This is where we save the list)
        sharedPreferences = getSharedPreferences("OneSecPrefs", Context.MODE_PRIVATE)

        val enableServiceButton: Button = findViewById(R.id.buttonEnableService)
        enableServiceButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }

        // Setup RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewApps)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter with empty list first
        adapter = AppAdapter(emptyList()) { packageName, isChecked ->
            if (isChecked) {
                addPackageToBlockList(packageName)
            } else {
                removePackageFromBlockList(packageName)
            }
        }
        recyclerView.adapter = adapter

        // Setup Search View
        val searchView: SearchView = findViewById(R.id.searchViewApps)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // We don't need to do anything when "enter" is pressed
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter the list whenever the text changes
                filterApps(newText)
                return true
            }
        })

        // Fetch apps
        lifecycleScope.launch(Dispatchers.IO) {
            // Get installed apps
            val apps = InstalledAppsUtils.getInstalledApps(this@MainActivity)
            val savedPackages = getSavedBlockedPackages()

            // Restore checkbox states
            apps.forEach { app ->
                app.isSelected = savedPackages.contains(app.packageName)
            }

            // Save the full list to our variable
            allAppsList = apps

            withContext(Dispatchers.Main) {
                // Show the full list initially
                adapter.updateList(allAppsList)
            }
        }
    }

    // --- Filtering Logic ---
    private fun filterApps(query: String?) {
        if (query.isNullOrEmpty()) {
            // If search is empty, show all apps
            adapter.updateList(allAppsList)
        } else {
            // Filter the list by app name
            val filteredList = allAppsList.filter { app ->
                app.appName.contains(query, ignoreCase = true)
            }
            adapter.updateList(filteredList)
        }
    }

    // --- Helper Functions for Saving Data ---

    private fun getSavedBlockedPackages(): Set<String> {
        return sharedPreferences.getStringSet("blocked_packages", emptySet()) ?: emptySet()
    }

    private fun addPackageToBlockList(packageName: String) {
        val currentList = getSavedBlockedPackages().toMutableSet()
        currentList.add(packageName)
        sharedPreferences.edit().putStringSet("blocked_packages", currentList).apply()
    }

    private fun removePackageFromBlockList(packageName: String) {
        val currentList = getSavedBlockedPackages().toMutableSet()
        currentList.remove(packageName)
        sharedPreferences.edit().putStringSet("blocked_packages", currentList).apply()
    }
}