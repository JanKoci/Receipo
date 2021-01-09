package com.example.receipo.ui.creation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.receipo.db.ReceiptDatabase
import com.example.receipo.db.entity.Receipt
import com.example.receipo.db.entity.Store

class StoreViewModel(application: Application): AndroidViewModel(application) {
    private val storeDao = ReceiptDatabase.getDatabase(application).storeDao()
    private val receiptDao = ReceiptDatabase.getDatabase(application).receiptDao()
    val storeList: LiveData<List<Store>>

    init {
        storeList = storeDao.allStores
    }

    suspend fun deleteStore(store: Store): Int? {
        val storeId = store.storeId
        val receipts: List<Receipt> = receiptDao.getByStore(storeId)

        if (receipts.isEmpty()) {
            storeDao.delete(store)
            return 0

        } else {
            return null
        }
    }
}