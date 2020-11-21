package com.example.receipo.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Item (
    @PrimaryKey val id: Int,
    val thumbNailPath: String?,
    val scanPath: String?,
    val description: String?,
    val price: Int?,
    val parentReceiptId: Int
)
