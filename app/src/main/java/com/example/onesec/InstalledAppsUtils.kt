package com.example.onesec

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log

object InstalledAppsUtils {

    fun getInstalledApps(context: Context): List<AppInfo> {
        val packageManager = context.packageManager
        val appsList = mutableListOf<AppInfo>()

        // Get ALL installed apps
        val allPackages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)

        for (packageInfo in allPackages) {
            // We want to filter out system apps (like "Android System", "Bluetooth", etc.)
            // Usually, system apps have the FLAG_SYSTEM set.
            val isSystemApp = (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0

            // However, we might want to allow blocking some updated system apps (like Chrome or Gmail).
            // A common trick is to check if it has been updated (FLAG_UPDATED_SYSTEM_APP).
            val isUpdatedSystemApp = (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0

            // Logic: Show it if it's NOT a system app, OR if it IS a system app but user installed an update
            if (!isSystemApp || isUpdatedSystemApp) {

                val appName = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                val packageName = packageInfo.packageName
                val icon = packageInfo.applicationInfo.loadIcon(packageManager)

                // Exclude our own app from the list!
                if (packageName != context.packageName) {
                    appsList.add(AppInfo(appName, packageName, icon))
                }
            }
        }

        // Sort the list alphabetically by app name
        return appsList.sortedBy { it.appName.lowercase() }
    }
}