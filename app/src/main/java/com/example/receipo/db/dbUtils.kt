package com.example.receipo.db

import com.example.receipo.db.dao.ReceiptDao
import com.example.receipo.db.entity.Category
import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Receipt
import com.example.receipo.db.entity.Store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun rePopulateDb(database: ReceiptDatabase?) {
    database?.let { db ->
        withContext(Dispatchers.IO) {
            val dao: ReceiptDao = db.receiptDao()

            dao.deleteAllReceipts()

            val storeOne = Store(
                Name = "Coop Jednota"
            )
            val storeThree = Store(
                Name = "Hruska"
            )
            val storeFour = Store(
                Name = "Sportisimo"
            )
            val storeFive = Store(
                Name = "Okay Elektro"
            )
            val storeSix = Store(
                Name = "Luxor"
            )
            val storeSeven = Store(
                Name = "Vecerka Truang Hong"
            )
            val storeTwo = Store(
                Name = "Tvarohovy Satecek"
            )

            val categoryOne = Category(
                Name = "Sport"
            )

            val categoryThree = Category(
                Name = "Sport"
            )
            val categoryFour = Category(
                Name = "Clothes"
            )
            val categoryFive = Category(
                Name = "Jewellery"
            )
            val categorySix = Category(
                Name = "Selfcare"
            )
            val categoryTwo = Category(
                Name = "Electronics"
            )

            val itemOne = Item(
                thumbNailPath = "lololol",
                scanPath = "lololol",
                description = "TShirt",
                price = 500,
                parentReceiptId = 1
            )

            val itemTwo = Item(
                thumbNailPath = "lololol",
                scanPath = "lololol",
                description = "Jeans",
                price = 1500,
                parentReceiptId = 1
            )

            val itemThree = Item(
                thumbNailPath = "lololol",
                scanPath = "lololol",
                description = "Coat",
                price = 5000,
                parentReceiptId = 1
            )

            val itemFour = Item(
                thumbNailPath = "lololol",
                scanPath = "lololol",
                description = "Jacket",
                price = 2500,
                parentReceiptId = 1
            )

            val itemFive = Item(
                thumbNailPath = "lololol",
                scanPath = "lololol",
                description = "Socks",
                price = 50,
                parentReceiptId = 1
            )

            val itemSix = Item(
                thumbNailPath = "lololol",
                scanPath = "lololol",
                description = "Headphones",
                price = 5000,
                parentReceiptId = 2
            )
            val itemSeven = Item(
                thumbNailPath = "lololol",
                scanPath = "lololol",
                description = "Mobile Phone",
                price = 25000,
                parentReceiptId = 2
            )


            val receiptOne = Receipt(
                creationDate = "29. 12. 2020",
                expirationDate = "29. 12. 2022",
                receiptStoreId = dao.insertStores(storeFour),
                categoryId = dao.insertCategory(categoryFour),
                thumbNailPath = "tralala",
                scanImagePath = "tralala",
                price = "30000"
            )

            val receiptTwo = Receipt(
                creationDate = "30. 12. 2020",
                expirationDate = "30. 12. 2025",
                receiptStoreId = dao.insertStores(storeFive),
                categoryId = dao.insertCategory(categoryTwo),
                thumbNailPath = "papapa",
                scanImagePath = "papapa",
                price = "9550"
            )

            val receiptThree = Receipt(
                creationDate = "3. 1. 2021",
                expirationDate = "3. 1. 2023",
                receiptStoreId = dao.insertStores(storeTwo),
                categoryId = dao.insertCategory(categoryOne),
                thumbNailPath = "papapa",
                scanImagePath = "papapa",
                price = "300"
            )

            val receiptFour = Receipt(
                creationDate = "25. 12. 2020",
                expirationDate = "25. 12. 2025",
                receiptStoreId = dao.insertStores(storeOne),
                categoryId = dao.insertCategory(categoryThree),
                thumbNailPath = "papapa",
                scanImagePath = "papapa",
                price = "400"
            )

            val receiptFive = Receipt(
                creationDate = "8. 6. 2020",
                expirationDate = "8. 6. 2023",
                receiptStoreId = dao.insertStores(storeThree),
                categoryId = dao.insertCategory(categoryFive),
                thumbNailPath = "papapa",
                scanImagePath = "papapa",
                price = "500"
            )

            val receiptSix = Receipt(
                creationDate = "15. 7. 1997",
                expirationDate = "15. 7. 1999",
                receiptStoreId = dao.insertStores(storeSix),
                categoryId = dao.insertCategory(categorySix),
                thumbNailPath = "papapa",
                scanImagePath = "papapa",
                price = "600"
            )

            val receiptSeven = Receipt(
                creationDate = "20. 9. 2020",
                expirationDate = "20. 9. 2022",
                receiptStoreId = dao.insertStores(storeSeven),
                categoryId = 2,
                thumbNailPath = "papapa",
                scanImagePath = "papapa",
                price = "700"
            )

            val receiptEight = Receipt(
                creationDate = "18. 1. 2020",
                expirationDate = "18. 1. 2022",
                receiptStoreId = 1,
                categoryId = 3,
                thumbNailPath = "papapa",
                scanImagePath = "papapa",
                price = "800"
            )

            dao.insertReceipts(
                receiptOne, receiptTwo, receiptThree,
                receiptFour, receiptFive,
                receiptSix, receiptSeven, receiptEight
            )
            dao.insertItem(
                itemOne, itemTwo, itemThree, itemFour,
                itemFive, itemSix, itemSeven
            )
        }
    }
}