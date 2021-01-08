package com.example.receipo.ui.creation

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.R
import com.example.receipo.model.ReceiptItem
import com.example.receipo.ui.creation.ItemsAdapter.ItemViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemsAdapter(val itemsList: MutableList<ReceiptItem>):
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View, val descriptionEditTextListener: DescriptionEditTextListener,
                                         val priceEditTextListener: PriceEditTextListener):
                RecyclerView.ViewHolder(itemView) {

        val description: EditText = itemView.findViewById(R.id.edit_description)
        val price: EditText = itemView.findViewById(R.id.edit_price)

        init {
            description.addTextChangedListener(descriptionEditTextListener)
            price.addTextChangedListener(priceEditTextListener)
        }

        fun bind(item: ReceiptItem) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_list_item, parent, false)

        return ItemViewHolder(view, DescriptionEditTextListener(), PriceEditTextListener())
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.descriptionEditTextListener.updatePosition(position)
        holder.priceEditTextListener.updatePosition(position)
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

    inner class DescriptionEditTextListener(): TextWatcher {
        var position: Int? = null

        fun updatePosition(pos: Int) {
            position = pos
        }

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (position != null) {
                itemsList[position!!].description = s.toString()
            }
        }
    }

    inner class PriceEditTextListener(): TextWatcher {
        var position: Int? = null

        fun updatePosition(pos: Int) {
            position = pos
        }

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            itemsList[position!!].price = s.toString().toDouble()
        }
    }

}