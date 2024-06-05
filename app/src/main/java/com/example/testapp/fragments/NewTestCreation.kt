package com.example.testapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.data.question.Question
import com.example.testapp.data.question.TestDao
import com.example.testapp.data.question.TestDatabase
import com.example.testapp.data.question.TestEntity
import com.example.testapp.databinding.FragmentNewTestCreationBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewTestCreation : Fragment() {

    private lateinit var binding: FragmentNewTestCreationBinding
    private val testDao: TestDao by lazy { context?.let { TestDatabase.getInstance(it).testDao() }!! }
    private var index = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTestCreationBinding.inflate(inflater, container, false)

        lifecycleScope.launch(Dispatchers.IO) {
            index = testDao.getAllTestes().size
        }

        binding.create.setOnClickListener {
            if( binding.count.text.isNotEmpty()) {

                val testEntity = TestEntity(
                    index,
                    -1,
                    -1.0,
                    binding.name.text.toString(),
                    binding.description.text.toString(),
                    binding.group.text.toString(),
                    binding.count.text.toString().toInt(),
                    listOf<Question>())

                lifecycleScope.launch(Dispatchers.IO) {
                    testDao.insertTest(testEntity)
                    withContext(Dispatchers.Main) {
                        val bundle = Bundle()
                        bundle.putInt("test_index", index)
                        findNavController().navigate(R.id.action_newTestCreation_to_questionsCreate, bundle)
                    }
                }
            }
        }


        return binding.root
    }

}