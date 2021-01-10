package com.example.receipo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.db.ReceiptDatabase
import com.example.receipo.db.ReceiptDatabase.Companion.DEFAULT_CATEGORY_ICON
import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Receipt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.*


class ReceiptListAdapter(val viewModel: ReceiptsViewModel):
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

        fun bind(receiptId: Long, shop: String, purchaseDate: String,
                 price: Double, items: String, iconId: Int) {
            val priceString = "${NumberFormat.getInstance().format(price)} KČ"
            shopTextView.text = shop
            shopTextView.tag = receiptId.toInt()
            dateTextView.text = purchaseDate
            priceTextView.text = priceString
            itemsTextView.text = items
            imageView.setImageResource(iconId)
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
        val item = receiptsList?.get(position)
        var store= ""
        var categoryIconId: Int = DEFAULT_CATEGORY_ICON
        var receiptItems: List<Item> = listOf()

        runBlocking {
            withContext(Dispatchers.IO) {
                store = viewModel.getStoreName(item!!.receiptStoreId)
                categoryIconId = viewModel.getCategory(item.categoryId).iconId
                receiptItems = viewModel.getItemsByReceipt(item.receiptId)
            }
        }
        if (item != null) {
            holder.bind(item.receiptId, store, item.creationDate,
                        calculatePrice(receiptItems), itemsToString(receiptItems), categoryIconId)
        }
    }

    fun getItemAtPosition(position: Int): Receipt? {
        return if (position in 0 until itemCount) receiptsList?.get(position) else null
    }

    private fun calculatePrice(items: List<Item>): Double {
        var sum = 0.0
        for (item in items) {
            sum += item.price
        }
        return sum
    }

    private fun itemsToString(items: List<Item>): String {
        return items.joinToString(separator = ", ", transform = {item -> item.description })
    }

}