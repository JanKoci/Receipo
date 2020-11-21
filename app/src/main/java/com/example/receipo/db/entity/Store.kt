package com.example.receipo.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Store (
    @PrimaryKey val StoreId: Int,
    val Name: String
)
