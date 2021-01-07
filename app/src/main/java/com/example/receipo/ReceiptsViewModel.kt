package com.example.receipo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.receipo.db.ReceiptDatabase
import com.example.receipo.db.dao.CategoryDao
import com.example.receipo.db.dao.ReceiptDao
import com.example.receipo.db.dao.StoreDao
import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Receipt

class ReceiptsViewModel(application: Application) : AndroidViewModel(application) {

    private val receiptDao: ReceiptDao = ReceiptDatabase.getDatabase(application).receiptDao()
    private val storeDao: StoreDao = ReceiptDatabase.getDatabase(application).storeDao()
    private val categoryDao : CategoryDao = ReceiptDatabase.getDatabase(application).categoryDao()
    val receiptList: LiveData<List<Receipt>>

    init {
        receiptList = receiptDao.allReceipts
    }

//    suspend fun getAll(): LiveData<List<Receipt>>  {
//        return receiptsDao.getAllReceipts()
//    }

    suspend fun insert(vararg receipts: Receipt) {
        receiptDao.insert(*receipts)
    }

    suspend fun getReceiptById(receiptId: Long): Receipt? {
        return receiptDao.getById(receiptId)
    }

    suspend fun update(receipt: Receipt) {
        receiptDao.update(receipt)
    }

    suspend fun delete(receipt: Receipt) {
        receiptDao.delete(receipt)
    }

    suspend fun getStoreName(storeId: Long): String {
        return storeDao.getById(storeId).name
    }

    suspend fun getCategoryName(categoryId: Long): String {
        return categoryDao.getById(categoryId).name
    }

    suspend fun getItemsByReceipt(receiptId: Long): List<Item> {
        return receiptDao.getItems(receiptId)
    }

    suspend fun getReceiptPrice(receiptId: Long): Double {
        return receiptDao.getPrice(receiptId)
    }


}