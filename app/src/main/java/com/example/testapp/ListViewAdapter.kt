package com.example.testapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListViewAdapter(
    context: Context,
    resource: Int,
    textViewResourceId: Int,
    objects: ArrayList<Item>
) : ArrayAdapter<Item>(context, resource, textViewResourceId, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem = convertView
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }
        val currentItem: Item? = getItem(position)

        val picture = listItem?.findViewById<ImageView>(R.id.background)
        currentItem?.let { picture?.setImageResource(it.getPicture()) }

        val title = listItem?.findViewById<TextView>(R.id.tvTitle)
        title?.text = currentItem?.getTitle()

        val percent = listItem?.findViewById<TextView>(R.id.percent)
        percent?.text = currentItem?.getPrecent().toString()

        return listItem!!
    }

}