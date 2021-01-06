package com.example.receipo.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Store (
    @PrimaryKey(autoGenerate = true)
    var storeId: Long = 0,
    val name: String
)
