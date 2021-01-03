package com.example.receipo.db.entity

import androidx.room.*

@Entity
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    var receiptId: Long = 0,
    var creationDate: String,
    var expirationDate: String?,
    var receiptStoreId: Long,
    var categoryId: Long,
    var thumbNailPath: String?,
    var scanImagePath: String?,
    var price: String
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