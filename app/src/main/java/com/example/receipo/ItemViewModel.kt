package com.example.receipo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.receipo.db.ReceiptDatabase
import com.example.receipo.db.dao.ItemDao
import com.example.receipo.db.entity.Item

class ItemViewModel(application: Application): AndroidViewModel(application) {
    private val itemDao: ItemDao = ReceiptDatabase.getDatabase(application).itemDao()

    suspend fun getAll(receiptId: Long): List<Item> {
        return itemDao.getAllByReceipt(receiptId = receiptId)
    }

    suspend fun insert(vararg items: Item) {
        itemDao.insert(*items)
    }

    suspend fun update(vararg items: Item) {
        itemDao.update(*items)
    }

    suspend fun deleteItems(vararg items: Item) {
        itemDao.delete(*items)
    }

}