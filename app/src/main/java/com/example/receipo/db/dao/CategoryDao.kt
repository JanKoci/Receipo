package com.example.receipo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receipo.db.entity.Category

@Dao
interface CategoryDao {
    @get: Query("SELECT * FROM category ORDER BY name")
    val allCategories: LiveData<List<Category>>

    @Query("SELECT * FROM category")
    suspend fun getAll(): List<Category>

    @Query("SELECT * FROM category WHERE categoryId=:id")
    suspend fun getById(id: Long): Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg category: Category): List<Long>

    @Delete
    suspend fun delete(category: Category)
}