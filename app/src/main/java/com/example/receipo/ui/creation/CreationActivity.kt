package com.example.receipo.ui.creation

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.receipo.R
import com.example.receipo.db.ReceiptDatabase
import com.example.receipo.db.ReceiptDatabase.Companion.DEFAULT_CATEGORY_ID
import com.example.receipo.db.entity.Item
import com.example.receipo.db.entity.Receipt
import com.example.receipo.db.entity.Store
import com.example.receipo.model.ReceiptItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDate

class CreationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: CreationViewModel
    private var categoryId: Long? = null
    private var storeId: Long? = null
    var purchaseDate: LocalDate? = null
    var items: List<ReceiptItem> = listOf()
    var scanPath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creation)
        setSupportActionBar(findViewById(R.id.creation_toolbar))

        viewModel = ViewModelProvider(this).get(CreationViewModel::class.java)

        // setup navigation graph
        val navController = findNavController(R.id.nav_creation_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.creation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHostFragment = supportFragmentManager.primaryNavigationFragment
        val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
            R.id.action_next -> {
                if (fragment is CategoryFragment) {
                    fragment.findNavController().navigate(R.id.category_to_store)

                } else if (fragment is StoreFragment) {
                    Toast.makeText(this, "Select a Store", Toast.LENGTH_LONG).show()

                } else if (fragment is NewStoreFragment) {
                    val editTextInput = findViewById<EditText>(R.id.new_store_edit_text).text.toString()
                    if (editTextInput == "") {
                        Toast.makeText(this, "Please, type in the name of your new store",
                                        Toast.LENGTH_LONG).show()
                    } else {
                        storeId = saveNewStoreToDatabase(editTextInput)
                        fragment.findNavController().navigate(R.id.new_store_to_date)
                    }

                } else if (fragment is DateFragment) {
                    fragment.findNavController().navigate(R.id.date_to_items)

                } else if (fragment is ItemsFragment) {
                    setItems(fragment)
                    fragment.findNavController().navigate(R.id.items_to_scan)

                } else if (fragment is ScanFragment || fragment is ImageFragment) {
                    Log.d("Creation", "category=$categoryId, store=$storeId, " +
                            "date=$purchaseDate, items=$items, scanPath=$scanPath")
                    saveToDatabase()
                    finish()
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun saveNewStoreToDatabase(storeName: String): Long? {
        val newStore = Store(name = storeName)
        var storeId: Long? = null
        runBlocking {
            withContext(Dispatchers.IO) {
                storeId = viewModel.insertStore(newStore)[0]
            }
        }
        return storeId
    }

    private fun saveToDatabase() {
        val purchaseDate = if (purchaseDate != null) purchaseDate else LocalDate.now()
        // TODO: 08/01/2021 Add expiry to View
        val expirationDate = purchaseDate!!.plusYears(2)

        if (categoryId == null) {
            categoryId = DEFAULT_CATEGORY_ID
        }

        runBlocking {
            withContext(Dispatchers.IO) {
                val receiptIdsList = viewModel.insertReceipt(
                                    Receipt(categoryId = categoryId!!,
                                            receiptStoreId = storeId!!,
                                            creationDate = purchaseDate.toString(),
                                            expirationDate = expirationDate.toString(),
                                            scanImagePath = scanPath))
                val receiptId = receiptIdsList[0]
                val items: List<Item> = items.map { item -> Item(description = item.description,
                                                                price = item.price,
                                                                parentReceiptId = receiptId,
                                                                scanPath = item.getImageUri())}
                viewModel.insertItems(*items.toTypedArray())
            }
        }
    }

    private fun setItems(fragment: ItemsFragment) {
        items = fragment.getItems()
    }

    fun chooseCategory(view: View) {
        categoryId = view.findViewById<TextView>(R.id.category_name).tag.toString().toLong()
        val navHostFragment = supportFragmentManager.primaryNavigationFragment
        val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        fragment?.findNavController()?.navigate(R.id.category_to_store)
    }

    fun chooseStore(view: View) {
        storeId = view.findViewById<TextView>(R.id.store_name).tag.toString().toLong()
        val navHostFragment = supportFragmentManager.primaryNavigationFragment
        val fragment = navHostFragment?.childFragmentManager?.fragments?.get(0)
        fragment?.findNavController()?.navigate(R.id.store_to_date)
    }
}