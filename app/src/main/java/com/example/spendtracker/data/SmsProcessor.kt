package com.example.spendtracker.data

import android.content.Context
import android.provider.Telephony
import com.example.spendtracker.database.TransactionDao
import com.example.spendtracker.database.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Reads SMS from the inbox and persists unique debit transactions into Room.
object SmsProcessor {

    // Regex to capture amounts like "INR 1,234.56" or "INR1234".
    private val amountRegex =
        Regex("""INR\s*([0-9,]+(?:\.[0-9]{1,2})?)""", RegexOption.IGNORE_CASE)

    // Entry point for syncing SMS-based transactions.
    suspend fun processInbox(context: Context, dao: TransactionDao) = withContext(Dispatchers.IO) {
        val cr = context.contentResolver
        val uri = Telephony.Sms.Inbox.CONTENT_URI
        val projection = arrayOf(
            Telephony.Sms._ID,
            Telephony.Sms.BODY,
            Telephony.Sms.DATE
        )

        // Only SMS that contain both "debited" and "INR" in the body.
        val selection = "${Telephony.Sms.BODY} LIKE ? AND ${Telephony.Sms.BODY} LIKE ?"
        val selectionArgs = arrayOf("%debited%", "%INR%")

        cr.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            val bodyIndex = cursor.getColumnIndexOrThrow(Telephony.Sms.BODY)
            val dateIndex = cursor.getColumnIndexOrThrow(Telephony.Sms.DATE)

            while (cursor.moveToNext()) {
                val body = cursor.getString(bodyIndex) ?: continue
                val timestamp = cursor.getLong(dateIndex)

                // Ignore duplicates based on SMS timestamp.
                if (dao.existsByTimestamp(timestamp)) continue

                val amount = extractAmount(body) ?: continue
                val entity = TransactionEntity(
                    amount = amount,
                    timestamp = timestamp,
                    rawMessage = body
                )
                dao.insert(entity)
            }
        }
    }

    // Extracts the first INR amount from the message using regex.
    private fun extractAmount(message: String): Double? {
        val match = amountRegex.find(message) ?: return null
        val raw = match.groupValues[1].replace(",", "")
        return raw.toDoubleOrNull()
    }
}

