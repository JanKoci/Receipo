package com.example.receipo.ui.creation

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.R
import com.example.receipo.db.entity.Item
import com.example.receipo.model.ReceiptItem
import com.example.receipo.ui.creation.ItemImageActivity.Companion.RESULT_DELETE
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class ItemsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ItemsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var itemsList: MutableList<ReceiptItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_items, container, false)

        viewManager = LinearLayoutManager(root.context)
        viewAdapter = ItemsAdapter(itemsList, this)

        recyclerView = root.findViewById<RecyclerView>(R.id.items_recycle_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val fab: FloatingActionButton = root.findViewById<FloatingActionButton>(R.id.items_fab)
        fab.setOnClickListener { view -> addNewItem(view) }

        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )

        return root
    }

    private fun addNewItem(view: View?) {
        val newItem = ReceiptItem("", 0.0)
        itemsList.add(newItem)
        viewAdapter.notifyItemInserted(itemsList.size - 1)
    }

    fun getItems(): List<ReceiptItem> {
        return viewAdapter.itemsList
    }


    lateinit var currentPhotoPath: String
    lateinit var currentPhotoUri: Uri
    var currentPosition by Delegates.notNull<Int>()

    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_DETAIL = 2

    fun startItemImageActivity(position: Int, uri: Uri) {
        currentPosition = position
        val intent = Intent(requireContext(), ItemImageActivity::class.java)
        intent.putExtra("imageUri", uri)

        startActivityForResult(intent, REQUEST_IMAGE_DETAIL)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


    fun dispatchTakePictureIntent(position: Int) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                null
            }
            // Continue only if the File was successfully created
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.receipo.android.fileprovider",
                    it
                )
                currentPhotoUri = photoURI
                currentPosition = position
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                // save currentUri to item
                viewAdapter.addItemUri(currentPosition, currentPhotoUri)
            }

        } else if(resultCode == RESULT_DELETE) {
            if (requestCode == REQUEST_IMAGE_DETAIL) {
                // delete image uri
                viewAdapter.deleteItemUri(currentPosition)
            }

        }
    }

}