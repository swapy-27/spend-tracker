package com.example.spendtracker.repository

import android.content.Context
import com.example.spendtracker.database.AppDatabase
import com.example.spendtracker.util.TimeUtils
import com.example.spendtracker.data.SmsProcessor

// Mediates between UI / workers and Room for transaction data & aggregations.
class TransactionRepository(context: Context) {

    private val db = AppDatabase.getInstance(context.applicationContext)
    private val dao = db.transactionDao()

    // Triggers SMS content provider scan and persists new debit transactions.
    suspend fun syncSms(context: Context) {
        SmsProcessor.processInbox(context, dao)
    }

    suspend fun getTotalBetween(start: Long, end: Long): Double {
        return dao.totalBetween(start, end)
    }

    suspend fun getDailySpend(): Double {
        val (start, end) = TimeUtils.todayRangeMillis()
        return getTotalBetween(start, end)
    }

    suspend fun getWeeklySpend(): Double {
        val (start, end) = TimeUtils.currentWeekRangeMillis()
        return getTotalBetween(start, end)
    }

    suspend fun getMonthlySpend(): Double {
        val (start, end) = TimeUtils.currentMonthRangeMillis()
        return getTotalBetween(start, end)
    }
}

