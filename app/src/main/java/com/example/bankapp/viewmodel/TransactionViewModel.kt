package com.example.bankapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class TransactionViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    fun performTransaction(senderAccount: String, receiverAccount: String, amount: Double) {
        // Get the sender's balance from Firestore
        firestore.collection("user").document(senderAccount)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val senderBalance = documentSnapshot.getDouble("balance") ?: 0.0
                println(senderBalance)

                if (senderBalance >= amount) {
                    // Sufficient funds, proceed with the transaction
                    updateBalances(senderAccount, receiverAccount, amount)
                } else {
                    // Insufficient funds, notify the user or handle the situation accordingly
                    showToast("Insufficient funds")
                }
            }
            .addOnFailureListener {
                // Handle any errors that occur while reading the sender's balance
                showToast("Failed to check balance")
            }
    }

    private fun updateBalances(senderAccount: String, receiverAccount: String, amount: Double) {
        // Perform the transfer and update the balances in Firestore
        val batch = firestore.batch()

        // Deduct the amount from the sender's balance
        val senderDocRef = firestore.collection("user").document(senderAccount)
        batch.update(senderDocRef, "balance", FieldValue.increment(-amount))

        // Add the amount to the receiver's balance
        val receiverDocRef = firestore.collection("user2").document(receiverAccount)
        batch.update(receiverDocRef, "balance", FieldValue.increment(amount))

        // Commit the batch write to update both balances atomically
        batch.commit()
            .addOnSuccessListener {
                // Transaction successful, write the transaction data to FireStore
                writeTransactionData(senderAccount, receiverAccount, amount)
            }
            .addOnFailureListener {
                // Handle any errors that occur during the batch update
                showToast("Failed to update balances")
            }
    }

    private fun writeTransactionData(senderAccount: String, receiverAccount: String, amount: Double) {
        // Write the transaction data to FireStore
        val transactionData = hashMapOf(
            "senderAccount" to senderAccount,
            "receiverAccount" to receiverAccount,
            "amount" to amount,
            "timestamp" to Timestamp.now() // Use Timestamp to store the current date and time
        )

        firestore.collection("user").document(senderAccount).collection("transaction")
            .add(transactionData)
            .addOnSuccessListener {
                // Transaction data is written to FireStore successfully
                showToast("Transaction successful")
            }
            .addOnFailureListener {
                // Handle any errors that occur during writing the data to FireStore
                showToast("Failed to write transaction data")
            }
        //write to receiver Account
        firestore.collection("user2").document(receiverAccount).collection("transaction")
            .add(transactionData)
            .addOnSuccessListener {
                // Transaction data is written to FireStore successfully
                showToast("Transaction successful")
            }
            .addOnFailureListener {
                // Handle any errors that occur during writing the data to FireStore
                showToast("Failed to write transaction data")
            }

    }
    private fun showToast(message: String) {
        // Implement your showToast function here or use the one you previously defined
        // This function shows a toast message to display information or errors.
    }
}
