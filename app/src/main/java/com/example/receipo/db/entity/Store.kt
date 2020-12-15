package com.example.receipo.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Store (
    @PrimaryKey val storeId: Int,
    val Name: String
)