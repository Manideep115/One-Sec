package com.example.onesec

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(
    private var apps: List<AppInfo>, // Changed to 'var' so we can update it
    private val onAppSelected: (String, Boolean) -> Unit
) : RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.imageAppIcon)
        val name: TextView = view.findViewById(R.id.textAppName)
        val checkBox: CheckBox = view.findViewById(R.id.checkboxApp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val app = apps[position]

        holder.name.text = app.appName
        holder.icon.setImageDrawable(app.icon)

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = app.isSelected

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            app.isSelected = isChecked
            onAppSelected(app.packageName, isChecked)
        }
    }

    override fun getItemCount() = apps.size

    // --- NEW FUNCTION ---
    // Call this to update the list when searching
    fun updateList(newApps: List<AppInfo>) {
        apps = newApps
        notifyDataSetChanged()
    }
}