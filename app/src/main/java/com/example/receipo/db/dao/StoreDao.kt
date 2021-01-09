package com.example.receipo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.receipo.db.entity.Store

@Dao
interface StoreDao {
    @get: Query("SELECT * FROM store ORDER BY name")
    val allStores: LiveData<List<Store>>

    @Query("SELECT * FROM store")
    suspend fun getAll(): List<Store>

    @Query("SELECT * FROM store WHERE storeId=:id")
    suspend fun getById(id: Long): Store

    @Query("SELECT * FROM store WHERE name=:name")
    suspend fun getByName(name: String): Store

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg store: Store): List<Long>

    @Delete
    suspend fun delete(store: Store)

}