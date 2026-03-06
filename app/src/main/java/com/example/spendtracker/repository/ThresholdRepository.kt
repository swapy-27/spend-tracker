package com.example.spendtracker.repository

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.spendtracker.data.ThresholdPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Single DataStore instance scoped to the application.
private val Context.dataStore by preferencesDataStore(name = "threshold_prefs")

// Handles reading/writing threshold values and last notification days via DataStore.
class ThresholdRepository(private val context: Context) {

    companion object {
        private val DAILY_THRESHOLD_KEY = doublePreferencesKey("daily_threshold")
        private val WEEKLY_THRESHOLD_KEY = doublePreferencesKey("weekly_threshold")
        private val MONTHLY_THRESHOLD_KEY = doublePreferencesKey("monthly_threshold")
        private val CUSTOM_MESSAGE_KEY = stringPreferencesKey("custom_message")

        private val LAST_DAILY_NOTIFIED_KEY = longPreferencesKey("last_daily_notified")
        private val LAST_WEEKLY_NOTIFIED_KEY = longPreferencesKey("last_weekly_notified")
        private val LAST_MONTHLY_NOTIFIED_KEY = longPreferencesKey("last_monthly_notified")
    }

    // Flow of current preferences, updated whenever DataStore changes.
    val preferencesFlow: Flow<ThresholdPreferences> = context.dataStore.data.map { prefs ->
        ThresholdPreferences(
            dailyThreshold = prefs[DAILY_THRESHOLD_KEY] ?: 0.0,
            weeklyThreshold = prefs[WEEKLY_THRESHOLD_KEY] ?: 0.0,
            monthlyThreshold = prefs[MONTHLY_THRESHOLD_KEY] ?: 0.0,
            customMessage = prefs[CUSTOM_MESSAGE_KEY] ?: "",
            lastDailyNotifiedEpochDay = prefs[LAST_DAILY_NOTIFIED_KEY] ?: -1L,
            lastWeeklyNotifiedEpochDay = prefs[LAST_WEEKLY_NOTIFIED_KEY] ?: -1L,
            lastMonthlyNotifiedEpochDay = prefs[LAST_MONTHLY_NOTIFIED_KEY] ?: -1L
        )
    }

    // Updates user thresholds and optional custom notification message.
    suspend fun updateThresholds(
        daily: Double,
        weekly: Double,
        monthly: Double,
        message: String
    ) {
        context.dataStore.edit { prefs ->
            prefs[DAILY_THRESHOLD_KEY] = daily
            prefs[WEEKLY_THRESHOLD_KEY] = weekly
            prefs[MONTHLY_THRESHOLD_KEY] = monthly
            prefs[CUSTOM_MESSAGE_KEY] = message
        }
    }

    // Mark that we've sent the daily threshold notification for the given day.
    suspend fun setLastDailyNotified(epochDay: Long) {
        context.dataStore.edit { prefs ->
            prefs[LAST_DAILY_NOTIFIED_KEY] = epochDay
        }
    }

    // Mark that we've sent the weekly threshold notification for the given day.
    suspend fun setLastWeeklyNotified(epochDay: Long) {
        context.dataStore.edit { prefs ->
            prefs[LAST_WEEKLY_NOTIFIED_KEY] = epochDay
        }
    }

    // Mark that we've sent the monthly threshold notification for the given day.
    suspend fun setLastMonthlyNotified(epochDay: Long) {
        context.dataStore.edit { prefs ->
            prefs[LAST_MONTHLY_NOTIFIED_KEY] = epochDay
        }
    }
}
