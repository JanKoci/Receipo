package com.example.receipo.ui.creation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.receipo.db.ReceiptDatabase
import com.example.receipo.db.entity.Store

class StoreViewModel(application: Application): AndroidViewModel(application) {
    private val storeDao = ReceiptDatabase.getDatabase(application).storeDao()
    val storeList: LiveData<List<Store>>

    init {
        storeList = storeDao.allStores
    }
}