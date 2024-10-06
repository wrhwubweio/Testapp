package com.example.testapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.R
import com.example.testapp.activities.PagesActivity
import com.example.testapp.adapters.ResultListAdapter
import com.example.testapp.data.question.MyTestesDB
import com.example.testapp.data.question.TestDao
import com.example.testapp.databinding.FragmentResultBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Result : Fragment() {
    private lateinit var binding: FragmentResultBinding
    private val testDao: TestDao by lazy { context?.let { MyTestesDB.getInstance(it).testDao() }!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentResultBinding.inflate(inflater, container, false)

        val gridView = binding.libraryGrid

        Log.d("MINE", arguments?.getBooleanArray("result")?.get(0).toString())

        val data: MutableList<Pair<Int, String>> = mutableListOf()
        val results = arguments?.getBooleanArray("result")
        var sum = 0
        if (results != null) {
            for (i in results.indices) {
                if(results.get(i)){
                    data.add(Pair(R.drawable.result_element_done, (i + 1).toString()))
                    sum++
                }
                else{
                    data.add(Pair(R.drawable.result_element_fail, (i + 1).toString()))
                }
            }
        }

        val adapter = ResultListAdapter(requireContext(), data)
        gridView.adapter = adapter
        val percentage = ((sum.toDouble() / data.size) * 100)
        binding.textResult.text = "$percentage%"

        GlobalScope.launch(Dispatchers.IO) {
            val test = testDao.getAllTestes().get(MainPage.getInstance().idTest)

            // Проверка на null
            if (test != null) {
                withContext(Dispatchers.Main) {
                    test.percentage = percentage
                    testDao.updateTest(test)
                }
            } else {
                // Обработка случая, когда test равен null
                Log.e("ResultFragment", "TestEntity не найден для ID: ${MainPage.getInstance().category}")
            }
        }

        binding.end.setOnClickListener{
            val intent = Intent(requireActivity(), PagesActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }
}