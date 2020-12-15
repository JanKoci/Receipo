package com.example.receipo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.receipo.db.dao.ReceiptDao
import com.example.receipo.db.entity.Receipt
import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Store
import com.example.receipo.db.entity.Category

@Database(entities = [
            Receipt::class,
            Item::class,
            Store::class,
            Category::class
          ],
          version = 1)
abstract class ReceiptDatabase : RoomDatabase() {
    abstract fun receiptDao(): ReceiptDao

    companion object {
        private var INSTANCE: ReceiptDatabase? = null
        private const val DB_NAME = "receipo_db"

        fun getDatabase(context: Context): ReceiptDatabase {
            if (INSTANCE == null) {
                synchronized(ReceiptDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, ReceiptDatabase::class.java, DB_NAME)
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}