package com.example.receipo.ui.creation

import android.graphics.BitmapFactory
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.R
import com.example.receipo.model.ReceiptItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemsAdapter(val itemsList: MutableList<ReceiptItem>, val context: ItemsFragment):
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View, val descriptionEditTextListener: DescriptionEditTextListener,
                                         val priceEditTextListener: PriceEditTextListener):
                RecyclerView.ViewHolder(itemView) {

        val description: EditText = itemView.findViewById(R.id.edit_description)
        val price: EditText = itemView.findViewById(R.id.edit_price)
        val imageButton: FloatingActionButton = itemView.findViewById(R.id.item_image_fab)

        init {
            description.addTextChangedListener(descriptionEditTextListener)
            price.addTextChangedListener(priceEditTextListener)
        }

        fun bind(item: ReceiptItem) {
            if (item.getImageUri() != null) {
                // scale down the image !!!
//                imageButton.setImageURI(item.getImageUri())
                setPic(item.getImageUri()!!)

            } else {
                imageButton.setImageResource(R.drawable.ic_menu_camera)
            }
        }

        private fun setPic(uri: Uri) {
            // Get the dimensions of the View
            val targetW: Int = imageButton.width
            val targetH: Int = imageButton.height

            val bmOptions = BitmapFactory.Options().apply {
                // Get the dimensions of the bitmap
                inJustDecodeBounds = true

                BitmapFactory.decodeFile(uri.path)

                val photoW: Int = outWidth
                val photoH: Int = outHeight

                // Determine how much to scale down the image
                val scaleFactor: Int = Math.max(1, Math.min(photoW / targetW, photoH / targetH))

                // Decode the image file into a Bitmap sized to fill the View
                inJustDecodeBounds = false
                inSampleSize = scaleFactor
            }

            BitmapFactory.decodeFile(uri.path, bmOptions)?.also { bitmap ->
                imageButton.setImageBitmap(bitmap)
            }
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
        val item = itemsList[position]
        holder.bind(item)

        if (item.getImageUri() == null) {
            holder.imageButton.setOnClickListener {
                context.dispatchTakePictureIntent(position)
            }

        } else {
            holder.imageButton.setOnClickListener {
                context.startItemImageActivity(position, item.getImageUri()!!)}
        }

    }


    fun addItemUri(position: Int, uri: Uri) {
        if (position in 0 until itemCount) {
            itemsList[position].setImageUri(uri)
            notifyDataSetChanged()
        }
    }

    fun deleteItemUri(position: Int) {
        if (position in 0 until itemCount) {
            itemsList[position].deleteImageUri()
            notifyDataSetChanged()
        }
    }

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
            if (!s.isNullOrEmpty()) {
                itemsList[position!!].price = s.toString().toDouble()
            }
        }
    }

}