package com.example.receipo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.receipo.db.ReceiptDatabase
import com.example.receipo.db.dao.ReceiptDao
import com.example.receipo.db.entity.Receipt

class ReceiptsViewModel(application: Application) : AndroidViewModel(application) {

    private val receiptsDao : ReceiptDao = ReceiptDatabase.getDatabase(application).receiptDao()

    suspend fun getAll(): LiveData<List<Receipt>>  {
        return receiptsDao.getAllReceipts()
    }

    suspend fun insert(vararg receipts: Receipt) {
        receiptsDao.insertReceipts(*receipts)
    }

    suspend fun update(receipt: Receipt) {
        receiptsDao.updateReceipts(receipt)
    }

    suspend fun deleteAll(receipt: Receipt) {
        receiptsDao.deleteReceipts(receipt)
    }


}