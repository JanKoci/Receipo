package com.example.receipo.ui.expired

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.receipo.R

class ExpiredFragment : Fragment() {

    private lateinit var expiredViewModel: ExpiredViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_expired, container, false)
        return root
    }
}