package com.example.receipo.ui.creation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.R
import com.example.receipo.model.ReceiptItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemsAdapter(val itemsList: MutableList<ReceiptItem>):
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        val button: FloatingActionButton? = itemView.findViewById(R.id.items_fab)

        fun bind(item: ReceiptItem) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_list_item, parent, false)
        return ItemViewHolder(view)
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        if (viewType == R.layout.items_list_item) {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.items_list_item, parent, false)
//            return ItemViewHolder(view)
//
//        } else {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.items_list_fab, parent, false)
//            return ItemViewHolder(view)
//        }
//    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        if (position == itemsList.size) {
//            holder.button?.setOnClickListener {
//                itemsList.add(ReceiptItem("", 0.0))
//                notifyItemInserted(itemsList.size - 1)
//            }
//
//        } else {
//            holder.bind(itemsList[position])
//        }
//
//    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

//    override fun getItemViewType(position: Int): Int {
//        if (position == itemsList.size) {
//            return R.layout.items_list_fab
//        } else {
//            return R.layout.items_list_item
//        }
//    }
}