package com.example.spendtracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionDao {

    // Inserts a transaction; ignore if timestamp (unique index) already exists.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: TransactionEntity)

    // Checks if a transaction already exists for a given SMS timestamp.
    @Query("SELECT EXISTS(SELECT 1 FROM transactions WHERE timestamp = :timestamp)")
    suspend fun existsByTimestamp(timestamp: Long): Boolean

    // Calculates the total spend between two timestamps (inclusive).
    @Query("SELECT IFNULL(SUM(amount), 0) FROM transactions WHERE timestamp BETWEEN :start AND :end")
    suspend fun totalBetween(start: Long, end: Long): Double
}

