package com.example.receipo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.DataSource
import com.example.receipo.R
import com.example.receipo.ReceiptListAdapter
import com.example.receipo.ReceiptsViewModel
import com.example.receipo.db.entity.Receipt

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var receiptsAdapter: ReceiptListAdapter
    private lateinit var receiptsViewModel: ReceiptsViewModel
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        viewManager = LinearLayoutManager(view.context)
        receiptsAdapter = ReceiptListAdapter(requireContext())
        recyclerView = view.findViewById<RecyclerView>(R.id.home_recycle_view).apply {
            layoutManager = viewManager
            adapter = receiptsAdapter
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )
    }

    private fun initData() {
        receiptsViewModel = ViewModelProvider(this).get(ReceiptsViewModel::class.java)
        receiptsViewModel.receiptList.observe(this,
            Observer { receipts: List<Receipt> ->
                receiptsAdapter.setReceiptList(receipts)
            }
        )
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}