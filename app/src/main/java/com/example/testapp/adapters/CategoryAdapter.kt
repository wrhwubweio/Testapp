package com.example.testapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.data.question.Categories

public interface OnClickCategoryListener {
    fun onAddClick(pos: Int)
}


class CategoryAdapter(private val categories: List<Categories>, private val onClickCategoryListener: OnClickCategoryListener) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val categoryNameTextView = view.findViewById<TextView>(R.id.category_name)
        private val addTest = view.findViewById<TextView>(R.id.add_test)

        fun bind(category: Categories) {
            categoryNameTextView.text = category.name
            Log.d("MINE", category.name)
            addTest.setOnClickListener {
                onClickCategoryListener.onAddClick(category.id)
            }
        }
    }

}











