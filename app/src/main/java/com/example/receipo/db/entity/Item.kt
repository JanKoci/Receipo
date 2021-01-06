package com.example.receipo.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
//    val thumbNailPath: String?,
    val scanPath: String?,
    val description: String,
    val price: Double,
    val parentReceiptId: Long
)
