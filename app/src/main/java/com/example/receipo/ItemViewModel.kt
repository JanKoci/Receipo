package com.example.receipo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.receipo.db.ReceiptDatabase
import com.example.receipo.db.dao.ReceiptDao
import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Receipt

class ItemViewModel(application: Application): AndroidViewModel(application) {
    private val dao: ReceiptDao = ReceiptDatabase.getDatabase(application).receiptDao()

    suspend fun getAll(receiptId: Long): List<Item> {
        return dao.getReceiptItems(receiptId = receiptId)
    }

    suspend fun insert(vararg items: Item) {
        dao.insertItem(*items)
    }

    suspend fun update(vararg items: Item) {
        dao.updateItems(*items)
    }

    suspend fun deleteItems(vararg items: Item) {
        dao.deleteItems(*items)
    }

}