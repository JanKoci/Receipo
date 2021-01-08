package com.example.receipo.ui.creation

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.receipo.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ScanFragment : Fragment() {

    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_GALLERY_CODE = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_scan, container, false)

        val cameraButton = root.findViewById<ImageButton>(R.id.scan_camera_button)
        cameraButton.setOnClickListener { dispatchTakePictureIntent()}

        val galleryButton = root.findViewById<Button>(R.id.scan_gallery_button)
        galleryButton.setOnClickListener { openGalleryForImage()}

        return root
    }

    lateinit var currentPhotoPath: String
    lateinit var currentPhotoUri: Uri

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


    private fun dispatchTakePictureIntent2() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                // Create the File where the photo should go
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
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }


    private fun dispatchTakePictureIntent() {
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }


    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    // manage taken photo
    // save the photo
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                // send scan uri to ImageFragment
                val bundle = bundleOf("scanUri" to currentPhotoUri)
                findNavController().navigate(R.id.scan_to_image, bundle)

            } else if (requestCode == REQUEST_GALLERY_CODE && data != null) {
//                Toast.makeText(requireContext(), "Gallery", Toast.LENGTH_LONG).show()
                // send scan uri to ImageFragment
                val bundle = bundleOf("scanUri" to currentPhotoUri)
                findNavController().navigate(R.id.scan_to_image, bundle)
            }
        } else {
            Toast.makeText(requireContext(), "CANCELED ", Toast.LENGTH_LONG).show()
        }
    }
}