package com.example.receipo.db.entity

import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Store
import androidx.room.*

@Entity
data class Receipt (
    @PrimaryKey val receiptId: Int,
    val creationDate: String?,
    val expirationDate: String?,
    val storeName: String?,
    val category: String?,
    val thumbNailPath: String?,
    val scanImagePath: String?,
    val price: String?
)

data class receiptWithItems(
    @Embedded val receipt: Receipt,
    @Relation(
        parentColumn = "receiptId",
        entityColumn = "parentReceiptId"
    )
    val items: List<Item>
)


data class StoreAndReceipt(
    @Embedded val store: Store,
    @Relation(
        parentColumn = "storeId",
        entityColumn = "storeName"
    )
    val receipts: List<Receipt>
)