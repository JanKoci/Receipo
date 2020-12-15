package com.example.receipo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.db.entity.Receipt

//class ReceiptListAdapter(val dataset: DataSource.MyData):
//    RecyclerView.Adapter<ReceiptListAdapter.ReceiptListViewHolder>() {
//
//    class ReceiptListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        private val shopTextView: TextView = itemView.findViewById(R.id.text_shop)
//        private val dateTextView: TextView = itemView.findViewById(R.id.text_date)
//        private val itemsTextView: TextView = itemView.findViewById(R.id.text_items)
//        private val priceTextView: TextView = itemView.findViewById(R.id.text_price)
//        private val imageView: ImageView = itemView.findViewById(R.id.item_image_view)
////        private val view: TextView = itemView.findViewById(R.id.item_name)
//
//        fun bind(shop: String, date: String, items: String, price: String) {
//            shopTextView.text = shop
//            dateTextView.text = date
//            itemsTextView.text = items
//            priceTextView.text = price
////            imageView.setImageResource(R.mipmap.ic_clothes_round)
////            view.text = word
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptListViewHolder {
//        val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.receipt_list_item, parent, false)
//        return ReceiptListViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return dataset.dates.size
//    }
//
//    override fun onBindViewHolder(holder: ReceiptListViewHolder, position: Int) {
//        holder.bind(dataset.shops[position],
//                    dataset.dates[position],
//                    dataset.items[position],
//                    dataset.prices[position])
//    }
//}

class ReceiptListAdapter(viewModel: ReceiptsViewModel):
    RecyclerView.Adapter<ReceiptListAdapter.ReceiptListViewHolder>() {

    private var model = viewModel

    class ReceiptListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val shopTextView: TextView = itemView.findViewById(R.id.text_shop)
        private val dateTextView: TextView = itemView.findViewById(R.id.text_date)
        private val itemsTextView: TextView = itemView.findViewById(R.id.text_items)
        private val priceTextView: TextView = itemView.findViewById(R.id.text_price)
        private val imageView: ImageView = itemView.findViewById(R.id.item_image_view)
//        private val view: TextView = itemView.findViewById(R.id.item_name)

        fun bind(shop: String, date: String, items: String, price: String) {
            shopTextView.text = shop
            dateTextView.text = date
            itemsTextView.text = items
            priceTextView.text = price
//            imageView.setImageResource(R.mipmap.ic_clothes_round)
//            view.text = word
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.receipt_list_item, parent, false)
        return ReceiptListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return receiptData.get
    }

    override fun onBindViewHolder(holder: ReceiptListViewHolder, position: Int) {
        holder.bind(dataset.shops[position],
            dataset.dates[position],
            dataset.items[position],
            dataset.prices[position])
    }

}