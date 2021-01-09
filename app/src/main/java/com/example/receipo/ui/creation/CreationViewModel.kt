package com.example.receipo.ui.creation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.receipo.db.ReceiptDatabase
import com.example.receipo.db.dao.CategoryDao
import com.example.receipo.db.dao.ItemDao
import com.example.receipo.db.dao.ReceiptDao
import com.example.receipo.db.dao.StoreDao
import com.example.receipo.db.entity.Category
import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Receipt
import com.example.receipo.db.entity.Store

class CreationViewModel(application: Application): AndroidViewModel(application) {
    private val categoryDao: CategoryDao = ReceiptDatabase.getDatabase(application).categoryDao()
    private val storeDao: StoreDao = ReceiptDatabase.getDatabase(application).storeDao()
    private val itemDao: ItemDao = ReceiptDatabase.getDatabase(application).itemDao()
    private val receiptDao: ReceiptDao = ReceiptDatabase.getDatabase(application).receiptDao()

//    suspend fun insertCategory(category: Category) {
//        categoryDao.insert(category)
//    }
//

    suspend fun insertStore(store: Store): List<Long> {
        return storeDao.insert(store)
    }

    suspend fun insertItems(vararg items: Item) {
        itemDao.insert(*items)
    }

    suspend fun insertReceipt(receipt: Receipt): List<Long> {
        return receiptDao.insert(receipt)
    }
}