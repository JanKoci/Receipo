package com.example.receipo.ui.creation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        private val categoryIconView: ImageView = itemView.findViewById(R.id.category_image_view)

        fun bind(name: String, id: Long, icon: Int) {
            categoryTextView.text = name
            categoryTextView.tag = id
            categoryIconView.setImageResource(icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_list_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList!!.get(position)
        holder.bind(category.name, category.categoryId, category.iconId)
    }

    override fun getItemCount(): Int {
        return if (categoryList == null) {
            0
        } else {
            categoryList!!.size
        }
    }
}