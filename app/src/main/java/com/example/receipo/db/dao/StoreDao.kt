package com.example.receipo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receipo.db.entity.Store

@Dao
interface StoreDao {
    @get: Query("SELECT * FROM store ORDER BY name")
    val allStores: LiveData<List<Store>>

    @Query("SELECT * FROM store")
    fun getAll(): List<Store>

    @Query("SELECT * FROM store WHERE storeId=:id")
    fun getById(id: Long): Store

    @Query("SELECT * FROM store WHERE name=:name")
    fun getByName(name: String): Store

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg store: Store): List<Long>

    @Delete
    fun delete(store: Store)

}