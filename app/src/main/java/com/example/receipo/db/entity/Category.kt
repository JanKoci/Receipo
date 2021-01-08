package com.example.receipo.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Category (
    @PrimaryKey(autoGenerate = true)
    var categoryId: Long = 0,
    val name: String
)
