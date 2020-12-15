package com.example.receipo.db.entity

import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Store
import androidx.room.*

@Entity
data class Receipt (
    @PrimaryKey val receiptId: Int,
    val creationDate: String?,
    val expirationDate: String?,
    val receiptStoreId: Int,
    val categoryId: Int,
    val thumbNailPath: String?,
    val scanImagePath: String?,
    val price: String?
)


data class ReceiptWithItems(
    @Embedded val receipt: Receipt,
    @Relation(
        parentColumn = "receiptId",
        entityColumn = "parentReceiptId"
    )
    val items: List<Item>
)


data class StoreWithReceipt(
    @Embedded val store: Store,
    @Relation(
        parentColumn = "receiptId",
        entityColumn = "receiptStoreId"
    )
    val receipts: List<Receipt>
)