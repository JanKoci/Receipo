package com.example.receipo.ui.creation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.receipo.db.ReceiptDatabase
import com.example.receipo.db.entity.Category

class CategoryViewModel(application: Application): AndroidViewModel(application) {
    private val categoryDao = ReceiptDatabase.getDatabase(application).categoryDao()
    val categoryList: LiveData<List<Category>>

    init {
        categoryList = categoryDao.allCategories
    }
}