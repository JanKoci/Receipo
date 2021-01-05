package com.example.receipo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.db.entity.Receipt



class ReceiptListAdapter(val context: Context):
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
//        private val view: TextView = itemView.findViewById(R.id.item_name)

        fun bind(receiptId: Long, shop: Long, purchaseDate: String, price: String) {
            shopTextView.text = shop.toString()
            shopTextView.tag = receiptId.toInt()
            dateTextView.text = purchaseDate
            priceTextView.text = price
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

        holder.bind(
            receiptsList?.get(position)!!.receiptId,
            receiptsList?.get(position)!!.receiptStoreId,
            receiptsList?.get(position)!!.creationDate,
//            receiptsList[position].,
            receiptsList?.get(position)!!.price
            )
    }

}