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
    fun getAll(): List<Receipt>

    @Query("SELECT * FROM item WHERE parentReceiptId=:receiptId")
    fun getItems(receiptId: Long): List<Item>

    @Query("SELECT * FROM receipt WHERE receiptId=:id")
    fun getById(id: Long): Receipt

    @Query("SELECT COUNT() FROM receipt")
    fun getCount(): Int

    @Query("DELETE FROM receipt")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg receipts: Receipt): List<Long>

    @Update
    fun update(vararg receipts: Receipt)

    @Delete
    fun delete(vararg receipts: Receipt)
}