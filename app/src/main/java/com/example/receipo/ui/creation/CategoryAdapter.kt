package com.example.receipo.ui.creation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.receipo.R
import com.example.receipo.db.entity.Category

class CategoryAdapter:
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryList: List<Category>? = null

    fun setList(categories: List<Category>) {
        categoryList = categories
        notifyDataSetChanged()
    }

    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val categoryTextView: TextView = itemView.findViewById(R.id.category_name)

        fun bind(name: String, id: Long, icon: Int) {
            categoryTextView.text = name
            categoryTextView.tag = id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_list_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList!!.get(position).name,
                    categoryList!!.get(position).categoryId, 0)
    }

    override fun getItemCount(): Int {
        return if (categoryList == null) {
            0
        } else {
            categoryList!!.size
        }
    }
}