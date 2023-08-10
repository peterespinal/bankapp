package com.example.bankapp.model

data class ItemData(
    val fieldName: String,
    val value: String,
    val itemType: ItemType,
    val allow: Boolean = false,
    val iconResId: Int
)

enum class ItemType {
    ICON_TEXT,
    SWITCH
}

