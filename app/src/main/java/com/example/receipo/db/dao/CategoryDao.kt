package com.example.receipo.db.dao

import androidx.room.*
import com.example.receipo.db.entity.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getAll(): List<Category>

    @Query("SELECT * FROM category WHERE categoryId=:id")
    fun getById(id: Long): Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg category: Category): List<Long>

    @Delete
    fun delete(category: Category)
}