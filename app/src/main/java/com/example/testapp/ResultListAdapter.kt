package com.example.testapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ResultListAdapter(private val context: Context, private val data: List<Pair<Int, String>>) : BaseAdapter() {
    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Any = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_result, parent, false)
        val (image, text) = data[position]

        view.findViewById<ImageView>(R.id.result_image).setImageResource(image)
        view.findViewById<TextView>(R.id.result_num_element).text = text

        return view
    }
}