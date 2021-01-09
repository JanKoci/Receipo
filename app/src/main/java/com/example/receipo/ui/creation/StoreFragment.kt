package com.example.receipo.ui.creation

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.R
import com.example.receipo.ReceiptListAdapter
import com.example.receipo.db.entity.Category
import com.example.receipo.db.entity.Store
import com.example.receipo.ui.SwipeToDeleteCallback
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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

        fabAddNew.setOnClickListener {
            findNavController().navigate(R.id.store_to_new_store)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0)
                    fabAddNew.hide()
                else if (dy < 0)
                    fabAddNew.show()
            }
        })

        setupDelete()
        return root
    }

    private fun setupDelete() {
        // setup delete on swipe
        val swipeHandler = object: SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // popup dialog = are you sure you want to delete
                val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            // remove item from database
                            removeFromDatabase(viewHolder as StoreAdapter.StoreViewHolder)
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            // cancel action
                            viewAdapter.notifyDataSetChanged()
                        }
                    }
                }
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.apply {
                    setMessage("Are you sure you want to delete this store ?")
                    setPositiveButton("Yes", dialogClickListener)
                    setNegativeButton("No", dialogClickListener)
                }
                alertDialog.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun removeFromDatabase(viewHolder: StoreAdapter.StoreViewHolder) {
        val store = viewAdapter.getItem(viewHolder.adapterPosition)
        var result: Int? = null

        if (store == null) {
            // TODO: 09/01/2021
            return
        }

        runBlocking {
            withContext(Dispatchers.IO) {
                result = viewModel.deleteStore(store)
            }
        }

        if (result == null) {
            Toast.makeText(requireContext(), "Cannot delete store that is used in a receipt",
                Toast.LENGTH_LONG).show()
        }
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