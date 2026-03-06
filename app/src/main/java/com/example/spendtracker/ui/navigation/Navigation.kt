package com.example.spendtracker.ui.navigation

// Simple enum for two-screen navigation graph.
sealed class Screen(val route: String, val title: String) {
    object Dashboard : Screen("dashboard", "Dashboard")
    object Settings : Screen("settings", "Thresholds")
}

