package com.example.spendtracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spendtracker.ui.ThresholdUiState

// Threshold settings screen: user can configure daily/weekly/monthly limits and a custom message.
@Composable
fun SettingsScreen(
    uiState: ThresholdUiState,
    onDailyChanged: (String) -> Unit,
    onWeeklyChanged: (String) -> Unit,
    onMonthlyChanged: (String) -> Unit,
    onMessageChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
    contentPadding: PaddingValues
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Threshold Settings",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.dailyThresholdInput,
                onValueChange = onDailyChanged,
                label = { Text("Daily threshold (₹)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.weeklyThresholdInput,
                onValueChange = onWeeklyChanged,
                label = { Text("Weekly threshold (₹)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.monthlyThresholdInput,
                onValueChange = onMonthlyChanged,
                label = { Text("Monthly threshold (₹)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.customMessageInput,
                onValueChange = onMessageChanged,
                label = { Text("Custom notification message") },
                singleLine = false,
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onSaveClicked,
                enabled = !uiState.isSaving
            ) {
                Text(text = if (uiState.isSaving) "Saving..." else "Save thresholds")
            }

            if (uiState.saveMessage != null) {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = uiState.saveMessage,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

