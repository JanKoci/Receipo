package com.example.receipo.db.dao

import androidx.room.*
import com.example.receipo.db.entity.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM item WHERE parentReceiptId=:receiptId")
    fun getAllByReceipt(receiptId: Long): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: Item): List<Long>

    @Update
    fun update(vararg items: Item)

    @Delete
    fun delete(vararg items: Item)
}