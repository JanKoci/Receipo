package com.example.receipo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Receipt
import java.time.LocalDate


class ReceiptListAdapter(val context: Context, val viewModel: ReceiptsViewModel):
    RecyclerView.Adapter<ReceiptListAdapter.ReceiptListViewHolder>() {

    private var receiptsList: List<Receipt>? = null

    fun setReceiptList(receipts: List<Receipt>) {
        receiptsList = receipts
        notifyDataSetChanged()
    }

    class ReceiptListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val shopTextView: TextView = itemView.findViewById(R.id.text_shop)
        private val dateTextView: TextView = itemView.findViewById(R.id.text_date)
        private val itemsTextView: TextView = itemView.findViewById(R.id.text_items)
        private val priceTextView: TextView = itemView.findViewById(R.id.text_price)
        private val imageView: ImageView = itemView.findViewById(R.id.item_image_view)

        fun bind(receiptId: Long, shop: String, purchaseDate: LocalDate, price: Double) {
            shopTextView.text = shop
            shopTextView.tag = receiptId.toInt()
            dateTextView.text = purchaseDate.toString()
            priceTextView.text = price.toString()
            // TODO: pri ukladani uctenky vytvorit vypis itemu
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
        return if (receiptsList == null) {
            0
        } else {
            receiptsList!!.size
        }
    }

    override fun onBindViewHolder(holder: ReceiptListViewHolder, position: Int) {
        // TODO: 06/01/2021 Should working with viewModel here be runBlocking ?
        val item = receiptsList?.get(position)
        val store: String = viewModel.getStoreName(item!!.receiptStoreId)
        val receiptItems = viewModel.getItemsByReceipt(item.receiptId)

        holder.bind(item.receiptId, store, item.creationDate, calculatePrice(receiptItems))
    }

    private fun calculatePrice(items: List<Item>): Double {
        var sum = 0.0
        for (item in items) {
            sum += item.price
        }
        return sum
    }

}