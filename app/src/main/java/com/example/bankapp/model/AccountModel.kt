package com.example.bankapp.model

data class AccountModel (
    val senderAccount: String,
    val receiverAccount: String,
    val amount: Double,
    val description:String
        )