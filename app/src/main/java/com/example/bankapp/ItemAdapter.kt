package com.example.bankapp

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val itemList: List<ClipData.Item>) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_recycle_preview, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.textViewTitle.text = currentItem.text
        holder.textViewAmount.text=currentItem.text
        // Set other views' data here based on the currentItem object
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
