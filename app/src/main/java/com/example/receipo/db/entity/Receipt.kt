package com.example.receipo.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate

@Entity
data class Receipt(
    @PrimaryKey(autoGenerate = true)
    var receiptId: Long = 0,
    var creationDate: LocalDate,
    var expirationDate: LocalDate?,
    var receiptStoreId: Long,
    var categoryId: Long,
//    var thumbNailPath: String?,
    var scanImagePath: String?
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