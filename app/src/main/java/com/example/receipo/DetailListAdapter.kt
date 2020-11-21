package com.example.receipo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DetailListAdapter(val dataset: DataSource.MyData):
    RecyclerView.Adapter<DetailListAdapter.DetailListViewHolder>() {

    class DetailListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.detail_item_name)
        private val priceTextView: TextView = itemView.findViewById(R.id.detail_item_price)

        fun bind(name: String, price: String) {
            nameTextView.text = name
            priceTextView.text = price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_list_item_detail, parent, false)
        return DetailListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataset.expiration_dates.size
    }

    override fun onBindViewHolder(holder: DetailListViewHolder, position: Int) {
        holder.bind(dataset.detail_items_array[position],
                    dataset.detail_prices_array[position])
    }
}