package com.example.bankapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
    val textViewTitle: TextView = itemView.findViewById(R.id.transaction_recycle_preview_txt)
    val textViewAmount: TextView = itemView.findViewById(R.id.transaction_recycle_preview_amount)
}