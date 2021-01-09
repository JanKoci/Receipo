package com.example.receipo.ui.creation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.R
import com.example.receipo.db.entity.Store

class StoreAdapter:
        RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    private var storeList: List<Store>? = null

    fun setList(stores: List<Store>) {
        storeList = stores
        notifyDataSetChanged()
    }

    class StoreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val storeTextView: TextView = itemView.findViewById(R.id.store_name)

        fun bind(name: String, id: Long) {
            storeTextView.text = name
            storeTextView.tag = id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.store_list_item, parent, false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = storeList!!.get(position)
        holder.bind(store.name, store.storeId)
    }

    override fun getItemCount(): Int {
        return if (storeList == null) {
            0
        } else {
            storeList!!.size
        }
    }

    fun getItem(position: Int): Store? {
        return if (position < itemCount) storeList?.get(position) else null
    }
}