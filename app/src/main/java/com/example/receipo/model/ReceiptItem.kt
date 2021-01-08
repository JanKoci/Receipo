package com.example.receipo.model

import android.net.Uri

class ReceiptItem(var description: String, var price: Double) {
    private val imageUri: Uri? = null

    fun setImageUri() {}

    fun getImageUri(): String? {
        return imageUri?.toString()
    }

    override fun toString(): String {
        return "description=$description, price=$price"
    }
}