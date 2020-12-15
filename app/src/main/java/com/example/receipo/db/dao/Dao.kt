package com.example.receipo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receipo.db.entity.Category
import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Receipt
import com.example.receipo.db.entity.Store

@Dao
interface ReceiptDao {

    @Query("SELECT * FROM item WHERE item.parentReceiptId=:receiptId ")
    fun getReceiptItems(receiptId: Int): LiveData<List<Item>>

    @Query("SELECT * FROM receipt WHERE receiptId=:receiptId")
    fun getReceiptById(receiptId: Int): Receipt?

    @Query("SELECT * FROM receipt ORDER BY creationDate ASC")
    fun getAllReceipts(): LiveData<List<Receipt>>

    @Query("SELECT COUNT(receiptId) FROM receipt")
    fun getReceiptCount(): Int;

    @Query("SELECT * FROM store")
    fun getAllStores() : List<Store>

    @Query("SELECT * FROM store WHERE store.storeId=:storeId")
    fun getStoreById(storeId: Int): Store?

    @Query("SELECT * FROM category")
    fun getAllCategories() : LiveData<List<Category>>

    @Query("SELECT * FROM category WHERE category.categoryId=:categoryId")
    fun getCategoryById(categoryId: Int): Category?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReceipts(vararg receipts: Receipt)

    //fun insertReceiptsAndItems(receipts: Receipt, items: List<Item>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStores(store: Store)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    @Update
    fun updateReceipts(vararg receipts: Receipt)

    @Delete
    fun deleteReceipts(vararg receipts: Receipt)


}