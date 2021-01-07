package com.example.receipo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Receipt

@Dao
interface ReceiptDao {
    @get: Query("SELECT * FROM receipt ORDER BY creationDate")
    val allReceipts: LiveData<List<Receipt>>

    @Query("SELECT * FROM receipt")
    suspend fun getAll(): List<Receipt>

    @Query("SELECT * FROM item WHERE parentReceiptId=:receiptId")
    suspend fun getItems(receiptId: Long): List<Item>

    @Query("SELECT * FROM receipt WHERE receiptId=:id")
    suspend fun getById(id: Long): Receipt

    @Query("SELECT SUM(price) FROM item WHERE parentReceiptId=:receiptId")
    suspend fun getPrice(receiptId: Long): Double

    @Query("SELECT COUNT() FROM receipt")
    suspend fun getCount(): Int

    @Query("DELETE FROM receipt")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg receipts: Receipt): List<Long>

    @Update
    suspend fun update(vararg receipts: Receipt)

    @Delete
    suspend fun delete(vararg receipts: Receipt)
}