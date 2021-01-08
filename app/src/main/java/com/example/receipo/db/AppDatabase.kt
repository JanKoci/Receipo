package com.example.receipo.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.receipo.db.dao.*
import com.example.receipo.db.entity.Category
import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Receipt
import com.example.receipo.db.entity.Store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [
            Receipt::class,
            Item::class,
            Store::class,
            Category::class
          ],
          version = 1)
abstract class ReceiptDatabase : RoomDatabase() {
    abstract fun receiptDao(): ReceiptDao
    abstract fun itemDao(): ItemDao
    abstract fun storeDao(): StoreDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        private var INSTANCE: ReceiptDatabase? = null
        const val DEFAULT_CATEGORY_ID: Long = 0
        const val DEFAULT_CATEGORY_NAME = "Other"
        private const val DB_NAME = "receipo_db"

        fun getDatabase(context: Context): ReceiptDatabase {
            if (INSTANCE == null) {
                synchronized(ReceiptDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, ReceiptDatabase::class.java, DB_NAME)
                            .addCallback(object: Callback(){
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    Log.d("ReceiptDB","Populating DB")
                                    GlobalScope.launch(Dispatchers.IO) { rePopulateDb(INSTANCE) }
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}