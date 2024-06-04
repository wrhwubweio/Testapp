package com.example.testapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.testapp.activities.CreateActivity
import com.example.testapp.adapters.OnTestCreateClickListener
import com.example.testapp.adapters.TestListAdapter
import com.example.testapp.data.question.Question
import com.example.testapp.data.question.TestDao
import com.example.testapp.data.question.TestDatabase
import com.example.testapp.databinding.FragmentCreateBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Create : Fragment(), OnTestCreateClickListener {
    private lateinit var binding: FragmentCreateBinding
    private lateinit var adapter: TestListAdapter
    private val testDao: TestDao by lazy { context?.let { TestDatabase.getInstance(it).testDao() }!! }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentCreateBinding.inflate(inflater, container, false)

        generateList(binding.root)

        binding.addTest.setOnClickListener{
            val intent = Intent(requireActivity(), CreateActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun generateList(view: View){
        val listView = binding.listView

        GlobalScope.launch(Dispatchers.IO) {
            val dataTestes = testDao.getTestes()
            withContext(Dispatchers.Main) {

                val listener = object : OnTestCreateClickListener {
                    override fun onStatClick(position: Int) {
                        lifecycleScope.launch {

                        }
                    }

                    override fun onEditClick(position: Int) {
                        lifecycleScope.launch {
                            val intent = Intent(requireActivity(), CreateActivity::class.java)
                            val bundle = Bundle()
                            bundle.putInt("test_index", adapter.getItem(position).id)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }

                    }

                    override fun onDeleteClick(position: Int) {
                        lifecycleScope.launch {
                            testDao.deleteTestById(adapter.getItem(position).id)
                            adapter.remove(position)
                        }
                    }
                }

                adapter = TestListAdapter(view.context, dataTestes.toMutableList(), listener)

                listView.setAdapter(adapter)
            }
        }


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