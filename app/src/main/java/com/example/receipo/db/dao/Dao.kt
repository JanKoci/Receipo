package com.example.receipo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receipo.db.entity.Category
import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Receipt
import com.example.receipo.db.entity.Store

@Dao
interface ReceiptDaoOld {
    @get: Query("SELECT * FROM receipt ORDER BY creationDate")
    val allReceipts: LiveData<List<Receipt>>

    @Query("SELECT * FROM item WHERE item.parentReceiptId=:receiptId")
    fun getReceiptItems(receiptId: Long): List<Item>

    @Query("SELECT * FROM receipt WHERE receiptId=:id LIMIT 1")
    fun getReceiptById(id: Long): Receipt?

//    @Query("SELECT * FROM receipt ORDER BY creationDate ASC")
//    fun getAllReceipts(): LiveData<List<Receipt>>

    @Query("SELECT COUNT() FROM receipt")
    fun getReceiptCount(): Int;

    @Query("SELECT * FROM store")
    fun getAllStores() : List<Store>

    @Query("SELECT * FROM store WHERE store.Name=:name")
    fun getStoreById(name: Int): Store?

    @Query("SELECT * FROM category")
    fun getAllCategories() : LiveData<List<Category>>

    @Query("SELECT * FROM category WHERE category.Name=:name")
    fun getCategoryById(name: String): Category?

    @Query("DELETE FROM receipt")
    fun deleteAllReceipts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStores(store: Store): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReceipts(vararg receipts: Receipt): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: Category): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(vararg items: Item): List<Long>

    @Update
    fun updateReceipts(vararg receipts: Receipt)

    @Update
    fun updateItems(vararg items: Item)


    @Delete
    fun deleteItems(vararg items: Item)

    @Delete
    fun deleteReceipts(vararg receipts: Receipt)


}