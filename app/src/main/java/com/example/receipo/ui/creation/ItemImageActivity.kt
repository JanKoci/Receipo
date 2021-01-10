package com.example.receipo.ui.creation

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.example.receipo.R

class ItemImageActivity : AppCompatActivity() {

    companion object {
        const val RESULT_DELETE = 3
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_image)
        setSupportActionBar(findViewById(R.id.item_image_toolbar))

        val uri = intent.getSerializableExtra("imageUri") as Uri
        findViewById<AppCompatImageView>(R.id.item_image_image_view).setImageURI(uri)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.item_image, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()

            R.id.action_delete -> {
                setResult(RESULT_DELETE)
                finish()
            }
        }
        return true
    }
}