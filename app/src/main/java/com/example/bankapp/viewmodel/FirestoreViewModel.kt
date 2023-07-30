package com.example.bankapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField

class FirestoreViewModel:ViewModel() {
    private val fireStore = FirebaseFirestore.getInstance()
    private val collectionRef = fireStore.collection("user")

    fun fetchAccount(): LiveData<List<String>> {
        val accountLiveData = MutableLiveData<List<String>>()

        // Perform Firestore query to get the list of cities
        collectionRef.get()
            .addOnSuccessListener { result ->
                val accountList = mutableListOf<String>()
                for (document in result) {
                    val account = document.get("name")
                    account?.let {
                        accountList.add(it.toString())
                    }

                }
                accountLiveData.value = accountList
            }
            .addOnFailureListener { exception ->
                // Handle error if needed
                accountLiveData.value = emptyList()
            }
        return accountLiveData
    }
}