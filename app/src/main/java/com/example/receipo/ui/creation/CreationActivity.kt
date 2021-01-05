package com.example.receipo.ui.creation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.receipo.R

class CreationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creation)
        setSupportActionBar(findViewById(R.id.creation_toolbar))

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
                    fragment.findNavController().navigate(R.id.store_to_date)

                } else if (fragment is DateFragment) {
                    fragment.findNavController().navigate(R.id.date_to_items)

                } else if (fragment is ItemsFragment) {
                    fragment.findNavController().navigate(R.id.items_to_scan)
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}