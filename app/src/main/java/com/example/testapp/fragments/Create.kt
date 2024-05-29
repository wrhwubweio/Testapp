package com.example.testapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.activities.CreateActivity
import com.example.testapp.adapters.OnTestCreateClickListener
import com.example.testapp.adapters.TestListAdapter
import com.example.testapp.databinding.FragmentCreateBinding


class Create : Fragment(), OnTestCreateClickListener {
    private lateinit var binding: FragmentCreateBinding
    private lateinit var adapter: TestListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentCreateBinding.inflate(inflater, container, false)

        generateList(binding.root)

        return binding.root
    }

    private fun generateList(view: View){
        val listView = binding.listView

        val data: MutableList<String> = mutableListOf(
            "тест по истории #1",
            "тест по истории #2",
            "тест по информатики #1",
        )

        adapter = TestListAdapter(view.context, data, this)
        listView.setAdapter(adapter)
    }

    override fun onStatClick(position: Int) {

    }

    override fun onEditClick(position: Int) {
        val intent = Intent(requireActivity(), CreateActivity::class.java)
        startActivity(intent)
    }

    override fun onDeleteClick(position: Int) {
        adapter.remove(position)
    }
}