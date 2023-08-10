package com.example.bankapp.model

import com.example.bankapp.R
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreDataModel {

    private val itemsCollection = FirebaseFirestore.getInstance().collection("person")

    fun fetchItemsData(callback: (List<ItemData>) -> Unit) {
        val itemList = mutableListOf<ItemData>()

        itemsCollection.get().addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot.documents) {
                val name = document.getString("name") ?: ""
                val email = document.getString("email") ?: ""
                val address = document.getString("address") ?: ""
                val phone = document.getString("phone") ?: ""


                // Create an ItemData object with the retrieved name as the value
                itemList.add(ItemData("Name", name, ItemType.ICON_TEXT, iconResId = R.drawable.ic_baseline_person_24))
                itemList.add(ItemData("Email", email, ItemType.ICON_TEXT, iconResId = R.drawable.ic_baseline_email_24))
                itemList.add(ItemData("Address", address, ItemType.ICON_TEXT, iconResId = R.drawable.ic_baseline_map_24))
                itemList.add(ItemData("Phone", phone, ItemType.ICON_TEXT, iconResId = R.drawable.ic_baseline_phone_24))
                itemList.add(ItemData("View", "View", ItemType.ICON_TEXT, iconResId = R.drawable.ic_baseline_preview_24))
                itemList.add(ItemData("Card", "Card", ItemType.ICON_TEXT, iconResId = R.drawable.ic_baseline_credit_card_24))
                itemList.add(ItemData("Allow", "Allow Transaction", ItemType.SWITCH,allow = true, iconResId = R.drawable.ic_baseline_credit_card_24))

            }

            callback.invoke(itemList)
        }
    }
}
