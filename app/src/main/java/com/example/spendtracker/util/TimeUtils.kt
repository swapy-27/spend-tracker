package com.example.spendtracker.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId

// Provides reusable time range helpers for daily / weekly / monthly aggregation.
object TimeUtils {

    fun todayRangeMillis(): Pair<Long, Long> {
        val zone = ZoneId.systemDefault()
        val today = LocalDate.now(zone)
        val start = today.atStartOfDay(zone).toInstant().toEpochMilli()
        val end = today.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1
        return start to end
    }

    fun currentWeekRangeMillis(): Pair<Long, Long> {
        val zone = ZoneId.systemDefault()
        val today = LocalDate.now(zone)
        val startOfWeek = today.with(DayOfWeek.MONDAY)
        val start = startOfWeek.atStartOfDay(zone).toInstant().toEpochMilli()
        val end = startOfWeek.plusDays(7).atStartOfDay(zone).toInstant().toEpochMilli() - 1
        return start to end
    }

    fun currentMonthRangeMillis(): Pair<Long, Long> {
        val zone = ZoneId.systemDefault()
        val today = LocalDate.now(zone)
        val startOfMonth = today.withDayOfMonth(1)
        val start = startOfMonth.atStartOfDay(zone).toInstant().toEpochMilli()
        val end = startOfMonth.plusMonths(1).atStartOfDay(zone).toInstant().toEpochMilli() - 1
        return start to end
    }

    // Epoch day used to ensure we only notify once per threshold per calendar day.
    fun currentEpochDay(): Long = LocalDate.now().toEpochDay()
}

