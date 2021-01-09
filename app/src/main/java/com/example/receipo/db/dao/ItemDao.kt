package com.example.receipo.db.dao

import androidx.room.*
import com.example.receipo.db.entity.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM item WHERE parentReceiptId=:receiptId")
    suspend fun getAllByReceipt(receiptId: Long): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg items: Item): List<Long>

    @Update
    suspend fun update(vararg items: Item)

    @Delete
    suspend fun delete(vararg items: Item)
}