package com.example.spendtracker.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// Represents a single debit transaction extracted from an SMS.
@Entity(
    tableName = "transactions",
    indices = [Index(value = ["timestamp"], unique = true)]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val timestamp: Long,
    val rawMessage: String
)

