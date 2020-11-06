package com.example.receipo

import android.content.Context

class DataSource(val context: Context) {
    fun getData(): MyData {
        return MyData(context.resources.getStringArray(R.array.shops_array),
                      context.resources.getStringArray(R.array.dates_array),
                      context.resources.getStringArray(R.array.items_array),
                      context.resources.getStringArray(R.array.price_array))
    }

    data class MyData(val shops: Array<String>,
                      val dates: Array<String>,
                      val items: Array<String>,
                      val prices: Array<String>)
}