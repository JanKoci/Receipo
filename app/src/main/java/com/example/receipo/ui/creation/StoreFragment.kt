package com.example.receipo.ui.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.R
import com.example.receipo.db.entity.Category
import com.example.receipo.db.entity.Store
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StoreFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: StoreAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: StoreViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoreViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_store, container, false)

        viewManager = LinearLayoutManager(root.context)
        viewAdapter = StoreAdapter()

        recyclerView = root.findViewById<RecyclerView>(R.id.store_recycle_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )
        // initialize data
        initData()

        val fabAddNew: ExtendedFloatingActionButton = root.findViewById(R.id.efab_store)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                    fabAddNew.hide()
                else if (dy < 0)
                    fabAddNew.show()
            }
        })

        return root
    }


    private fun initData() {
        // initialize stores in adapter and setup observer
        viewModel.storeList.observe(viewLifecycleOwner,
            Observer { stores: List<Store> ->
                viewAdapter.setList(stores)
            }
        )
    }
}