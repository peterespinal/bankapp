package com.example.bankapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bankapp.model.ItemData
import com.example.bankapp.model.ItemType
import com.google.android.material.switchmaterial.SwitchMaterial

class ItemsAdapter(private var items: List<ItemData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // View types to handle different item layouts
    private val VIEW_TYPE_TEXT = 1
    private val VIEW_TYPE_SWITCH = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_TEXT -> {
                val itemView = inflater.inflate(R.layout.item_layout_icon_text, parent, false)
                TextViewHolder(itemView)
            }
            VIEW_TYPE_SWITCH -> {
                val itemView = inflater.inflate(R.layout.item_layout_icon_text_switch, parent, false)
                SwitchViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder) {
            is TextViewHolder -> {
                // Display icon and text
                holder.iconImageView.setImageResource(item.iconResId)
                holder.textView.text = item.value
            }
            is SwitchViewHolder -> {
                // Display switch
                holder.switch.text = item.value
                holder.switch.isChecked = item.allow
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return if (item.itemType == ItemType.SWITCH) VIEW_TYPE_SWITCH else VIEW_TYPE_TEXT
    }

    override fun getItemCount(): Int = items.size

    inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    inner class SwitchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val switch: SwitchMaterial = itemView.findViewById(R.id.switchCompat)
    }
    fun setItems(newItems: List<ItemData>) {
        items = newItems
        notifyDataSetChanged()
    }
}
