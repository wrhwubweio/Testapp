package com.example.testapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.testapp.R
import com.example.testapp.data.question.TestEntity

public interface OnTestCreateClickListener {
    fun onStatClick(position: Int)
    fun onEditClick(position: Int)
    fun onDeleteClick(position: Int)
}

class TestListAdapter(private val context: Context, private var data: MutableList<TestEntity>,
                      private val onTestCreateClickListener: OnTestCreateClickListener) : BaseAdapter() {
    override fun getCount(): Int = data.size

    override fun getItem(position: Int): TestEntity = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    fun remove(position: Int) {
        data.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_create_test, parent, false)
        val test = data[position]

        view.findViewById<TextView>(R.id.name).text = test.name

        view.findViewById<Button>(R.id.statistic).setOnClickListener{
            onTestCreateClickListener.onStatClick(position)
        }

        view.findViewById<Button>(R.id.edit).setOnClickListener{
            onTestCreateClickListener.onEditClick(position)
        }

        view.findViewById<Button>(R.id.delete).setOnClickListener{
            onTestCreateClickListener.onDeleteClick(position)
        }

        return view
    }

}