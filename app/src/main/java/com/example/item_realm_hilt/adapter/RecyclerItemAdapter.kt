package com.example.item_realm_hilt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.item_realm_hilt.R
import com.example.item_realm_hilt.model.Item


class RecyclerItemAdapter(val context: Context, val itemList: List<Item>) :
    RecyclerView.Adapter<RecyclerItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerItemAdapter.ItemViewHolder {

        return ItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.single_item_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemAdapter.ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvItemName.text = item.item_name
        holder.tvItemPrice.text = item.item_price
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItemName: TextView = view.findViewById(R.id.tvItemName)
        val tvItemPrice: TextView = view.findViewById(R.id.tvItemPrice)
        val imgItem: ImageView = view.findViewById(R.id.imgItem)
    }
}