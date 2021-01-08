package com.example.receipo.ui.creation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.R
import com.example.receipo.db.entity.Item
import com.example.receipo.model.ReceiptItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
        viewAdapter = ItemsAdapter(itemsList)

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

}