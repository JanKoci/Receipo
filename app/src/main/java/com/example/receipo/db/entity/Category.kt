package com.example.receipo.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Category (
    @PrimaryKey val CategoryId: Int,
    val Category: String
)
