package com.example.receipo.ui.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.R
import com.example.receipo.db.entity.Category
import com.example.receipo.db.entity.Receipt
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class CategoryFragment : Fragment() {

    private lateinit var viewModel: CategoryViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: CategoryAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val sharedViewModel: SharedViewModel by navGraphViewModels(R.id.nav_category)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_category, container, false)

        viewManager = LinearLayoutManager(root.context)
        viewAdapter = CategoryAdapter()

        recyclerView = root.findViewById<RecyclerView>(R.id.category_recycle_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recyclerView.addItemDecoration(
            DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        )
        initData()

        val fabAddNew: ExtendedFloatingActionButton = root.findViewById(R.id.efab_category)

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
        // initialize categories in adapter and setup observer
        viewModel.categoryList.observe(viewLifecycleOwner,
            Observer { categories: List<Category> ->
                viewAdapter.setList(categories)
            }
        )
    }


}