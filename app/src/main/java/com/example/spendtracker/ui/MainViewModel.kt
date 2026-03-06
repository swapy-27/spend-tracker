package com.example.spendtracker.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.spendtracker.repository.TransactionRepository
import com.example.spendtracker.repository.ThresholdRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// UI state for the dashboard screen.
data class DashboardUiState(
    val dailySpend: Double = 0.0,
    val weeklySpend: Double = 0.0,
    val monthlySpend: Double = 0.0,
    val isSyncing: Boolean = false,
    val errorMessage: String? = null
)

// UI state for the threshold settings screen.
data class ThresholdUiState(
    val dailyThresholdInput: String = "",
    val weeklyThresholdInput: String = "",
    val monthlyThresholdInput: String = "",
    val customMessageInput: String = "",
    val isSaving: Boolean = false,
    val saveMessage: String? = null
)

// Central ViewModel that coordinates SMS syncing, spend aggregation, and thresholds.
class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val appContext: Context = application.applicationContext
    private val transactionRepository = TransactionRepository(appContext)
    private val thresholdRepository = ThresholdRepository(appContext)

    private val _dashboardUiState = MutableStateFlow(DashboardUiState())
    val dashboardUiState: StateFlow<DashboardUiState> = _dashboardUiState

    private val _thresholdUiState = MutableStateFlow(ThresholdUiState())
    val thresholdUiState: StateFlow<ThresholdUiState> = _thresholdUiState

    init {
        // Load initial thresholds into UI and compute initial spend aggregates.
        observeThresholds()
        refreshSpending()
    }

    private fun observeThresholds() {
        viewModelScope.launch {
            thresholdRepository.preferencesFlow.collect { prefs ->
                _thresholdUiState.update {
                    it.copy(
                        dailyThresholdInput = if (prefs.dailyThreshold > 0) prefs.dailyThreshold.toString() else "",
                        weeklyThresholdInput = if (prefs.weeklyThreshold > 0) prefs.weeklyThreshold.toString() else "",
                        monthlyThresholdInput = if (prefs.monthlyThreshold > 0) prefs.monthlyThreshold.toString() else "",
                        customMessageInput = prefs.customMessage
                    )
                }
            }
        }
    }

    // Recomputes daily/weekly/monthly spend from the database.
    fun refreshSpending() {
        viewModelScope.launch {
            try {
                val daily = transactionRepository.getDailySpend()
                val weekly = transactionRepository.getWeeklySpend()
                val monthly = transactionRepository.getMonthlySpend()
                _dashboardUiState.update {
                    it.copy(
                        dailySpend = daily,
                        weeklySpend = weekly,
                        monthlySpend = monthly,
                        isSyncing = false,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _dashboardUiState.update {
                    it.copy(
                        isSyncing = false,
                        errorMessage = e.localizedMessage ?: "Failed to compute spending"
                    )
                }
            }
        }
    }

    // Triggered from the Sync SMS button after READ_SMS permission is granted.
    fun syncSms() {
        viewModelScope.launch {
            _dashboardUiState.update { it.copy(isSyncing = true, errorMessage = null) }
            try {
                transactionRepository.syncSms(appContext)
                refreshSpending()
            } catch (e: Exception) {
                _dashboardUiState.update {
                    it.copy(
                        isSyncing = false,
                        errorMessage = e.localizedMessage ?: "Failed to sync SMS"
                    )
                }
            }
        }
    }

    fun onDailyThresholdChanged(value: String) {
        _thresholdUiState.update { it.copy(dailyThresholdInput = value) }
    }

    fun onWeeklyThresholdChanged(value: String) {
        _thresholdUiState.update { it.copy(weeklyThresholdInput = value) }
    }

    fun onMonthlyThresholdChanged(value: String) {
        _thresholdUiState.update { it.copy(monthlyThresholdInput = value) }
    }

    fun onCustomMessageChanged(value: String) {
        _thresholdUiState.update { it.copy(customMessageInput = value) }
    }

    // Persists thresholds to DataStore.
    fun saveThresholds() {
        viewModelScope.launch {
            _thresholdUiState.update { it.copy(isSaving = true, saveMessage = null) }

            val current = thresholdUiState.value

            val daily = current.dailyThresholdInput.toDoubleOrNull() ?: 0.0
            val weekly = current.weeklyThresholdInput.toDoubleOrNull() ?: 0.0
            val monthly = current.monthlyThresholdInput.toDoubleOrNull() ?: 0.0
            val message = current.customMessageInput

            try {
                thresholdRepository.updateThresholds(
                    daily = daily,
                    weekly = weekly,
                    monthly = monthly,
                    message = message
                )
                _thresholdUiState.update {
                    it.copy(
                        isSaving = false,
                        saveMessage = "Thresholds saved"
                    )
                }
            } catch (e: Exception) {
                _thresholdUiState.update {
                    it.copy(
                        isSaving = false,
                        saveMessage = "Failed to save thresholds"
                    )
                }
            }
        }
    }
}

// Factory to create MainViewModel from an Activity/Compose environment.
class MainViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

