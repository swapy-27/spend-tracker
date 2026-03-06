package com.example.spendtracker

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.spendtracker.worker.ThresholdCheckWorker
import java.util.concurrent.TimeUnit

class SpendTrackerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        scheduleThresholdCheckWorker()
    }

    // Schedules a daily worker that checks spend against thresholds.
    private fun scheduleThresholdCheckWorker() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<ThresholdCheckWorker>(
            1, TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "threshold_check_work",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}

