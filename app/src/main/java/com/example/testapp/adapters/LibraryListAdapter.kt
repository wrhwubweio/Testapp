package com.example.testapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.testapp.R

class LibraryListAdapter(private val context: Context, private val data: List<Pair<Int, String>>) : BaseAdapter() {
    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Any = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_library, parent, false)
        val (image, text2) = data[position]


        view.findViewById<ImageView>(R.id.library_image).setImageResource(image)

        return view
    }
}