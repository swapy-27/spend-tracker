package com.example.spendtracker.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.spendtracker.repository.TransactionRepository
import com.example.spendtracker.repository.ThresholdRepository
import com.example.spendtracker.util.NotificationUtils
import com.example.spendtracker.util.TimeUtils
import kotlinx.coroutines.flow.first

// Periodic worker that aggregates spend and fires notifications when thresholds are exceeded.
class ThresholdCheckWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val context = applicationContext
        val transactionRepo = TransactionRepository(context)
        val thresholdRepo = ThresholdRepository(context)

        val thresholds = thresholdRepo.preferencesFlow.first()
        val todayEpoch = TimeUtils.currentEpochDay()

        val dailySpend = transactionRepo.getDailySpend()
        val weeklySpend = transactionRepo.getWeeklySpend()
        val monthlySpend = transactionRepo.getMonthlySpend()

        val baseMessage = if (thresholds.customMessage.isNotBlank()) {
            thresholds.customMessage
        } else {
            "Your spending has exceeded a configured limit."
        }

        // Only notify once per threshold per day.
        if (thresholds.dailyThreshold > 0 &&
            dailySpend > thresholds.dailyThreshold &&
            thresholds.lastDailyNotifiedEpochDay != todayEpoch
        ) {
            NotificationUtils.showThresholdNotification(
                context,
                title = "Daily spend limit exceeded",
                message = "$baseMessage\nDaily spend: ₹${"%.2f".format(dailySpend)}",
                notificationId = 1001
            )
            thresholdRepo.setLastDailyNotified(todayEpoch)
        }

        if (thresholds.weeklyThreshold > 0 &&
            weeklySpend > thresholds.weeklyThreshold &&
            thresholds.lastWeeklyNotifiedEpochDay != todayEpoch
        ) {
            NotificationUtils.showThresholdNotification(
                context,
                title = "Weekly spend limit exceeded",
                message = "$baseMessage\nWeekly spend: ₹${"%.2f".format(weeklySpend)}",
                notificationId = 1002
            )
            thresholdRepo.setLastWeeklyNotified(todayEpoch)
        }

        if (thresholds.monthlyThreshold > 0 &&
            monthlySpend > thresholds.monthlyThreshold &&
            thresholds.lastMonthlyNotifiedEpochDay != todayEpoch
        ) {
            NotificationUtils.showThresholdNotification(
                context,
                title = "Monthly spend limit exceeded",
                message = "$baseMessage\nMonthly spend: ₹${"%.2f".format(monthlySpend)}",
                notificationId = 1003
            )
            thresholdRepo.setLastMonthlyNotified(todayEpoch)
        }

        return Result.success()
    }
}

