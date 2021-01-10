package com.example.receipo.model

import android.net.Uri

class ReceiptItem(var description: String, var price: Double) {
    private var imageUri: Uri? = null

    fun setImageUri(uri: Uri) {
        imageUri = uri
    }

    fun deleteImageUri() {
        imageUri = null
    }

    fun getImageUri(): Uri? {
        return imageUri
    }

    fun getImageUriString(): String? {
        return imageUri?.toString()
    }

    override fun toString(): String {
        return "description=$description, price=$price"
    }
}