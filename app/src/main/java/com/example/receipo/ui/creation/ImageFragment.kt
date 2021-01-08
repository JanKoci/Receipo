package com.example.receipo.ui.creation

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.receipo.R

class ImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_image, container, false)
        val uri = arguments?.get("scanUri") as Uri

        val imageView = root.findViewById<ImageView>(R.id.scan_image_view)
        imageView.setImageURI(null)
        imageView.setImageURI(uri)

        (activity as CreationActivity).scanPath = uri.toString()
        return root
    }

}