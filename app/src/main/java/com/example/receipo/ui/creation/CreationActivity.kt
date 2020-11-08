package com.example.receipo.ui.creation

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.receipo.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class CreationActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creation)
        setSupportActionBar(findViewById(R.id.creation_toolbar))

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        findViewById<ExtendedFloatingActionButton>(R.id.efab_creation).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

//        val navController = findNavController(R.id.nav_creation_fragment)
//        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_category))
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.creation, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        this.finish()
        return true
    }
}