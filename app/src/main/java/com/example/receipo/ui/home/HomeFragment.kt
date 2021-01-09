package com.example.receipo.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.R
import com.example.receipo.ReceiptListAdapter
import com.example.receipo.ReceiptsViewModel
import com.example.receipo.db.entity.Receipt
import com.example.receipo.ui.SwipeToDeleteCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var receiptsAdapter: ReceiptListAdapter
    private lateinit var receiptsViewModel: ReceiptsViewModel
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receiptsViewModel = ViewModelProvider(this).get(ReceiptsViewModel::class.java)
        initData()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initView(root)
        return root
    }

    private fun initView(view: View) {
        viewManager = LinearLayoutManager(requireContext())
        receiptsAdapter = ReceiptListAdapter(receiptsViewModel)

        recyclerView = view.findViewById<RecyclerView>(R.id.home_recycle_view).apply {
            layoutManager = viewManager
            adapter = receiptsAdapter
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )

        // setup delete on swipe
        val swipeHandler = object: SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // popup dialog = are you sure you want to delete
                val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            // remove item from database
                            removeFromDatabase(viewHolder as ReceiptListAdapter.ReceiptListViewHolder)
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            // cancel action
                            receiptsAdapter.notifyDataSetChanged()
                        }
                    }
                }
                val alertDialog = AlertDialog.Builder(requireContext())
                alertDialog.apply {
                    setMessage("Are you sure you want to delete this receipt ?")
                    setPositiveButton("Yes", dialogClickListener)
                    setNegativeButton("No", dialogClickListener)
                }
                alertDialog.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun initData() {
        receiptsViewModel.receiptList.observe(this,
            Observer { receipts: List<Receipt> ->
                receiptsAdapter.setReceiptList(receipts)
            }
        )
    }

    private fun removeFromDatabase(viewHolder: ReceiptListAdapter.ReceiptListViewHolder) {
        // remove all items
        val position = viewHolder.adapterPosition
        val receipt = receiptsAdapter.getItemAtPosition(position)

        if (receipt == null) {
            // TODO: 09/01/2021
            return
        }

        runBlocking {
            withContext(Dispatchers.IO) {
                receiptsViewModel.deleteWithItems(receipt)
            }
        }

    }
}