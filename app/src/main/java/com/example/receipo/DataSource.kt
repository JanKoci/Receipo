package com.example.receipo

import android.content.Context

class DataSource(val context: Context) {
    fun getData(): MyData {
        return MyData(context.resources.getStringArray(R.array.shops_array),
                      context.resources.getStringArray(R.array.dates_array),
                      context.resources.getStringArray(R.array.items_array),
                      context.resources.getStringArray(R.array.price_array),
                      context.resources.getStringArray(R.array.creation_dates_array),
                      context.resources.getStringArray(R.array.expiration_dates_array),
                      context.resources.getStringArray(R.array.detail_items_array),
                      context.resources.getStringArray(R.array.detail_prices_array))
    }

    data class MyData(val shops: Array<String>,
                      val dates: Array<String>,
                      val items: Array<String>,
                      val prices: Array<String>,
                      val creation_dates: Array<String>,
                      val expiration_dates: Array<String>,
                      val detail_items_array: Array<String>,
                      val detail_prices_array: Array<String>)
}