package com.example.bankapp.model

import java.sql.Timestamp

data class TransactionHistoryItem(
    val transactionType: String, // e.g., "Credit" or "Debit"
    val transactionAmount: Double,
    val description:String,
    val transactionDate: Timestamp // You can use a Date or Long timestamp here
)