package com.example.spendtracker.data

// In-memory representation for DataStore-stored thresholds & last notification markers.
data class ThresholdPreferences(
    val dailyThreshold: Double = 0.0,
    val weeklyThreshold: Double = 0.0,
    val monthlyThreshold: Double = 0.0,
    val customMessage: String = "",
    val lastDailyNotifiedEpochDay: Long = -1L,
    val lastWeeklyNotifiedEpochDay: Long = -1L,
    val lastMonthlyNotifiedEpochDay: Long = -1L
)

