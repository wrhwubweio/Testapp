package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.databinding.FragmentResultBinding

class Result : Fragment() {
    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentResultBinding.inflate(inflater, container, false)

        val gridView = binding.libraryGrid

        val data: List<Pair<Int, String>> = listOf(
            Pair(R.drawable.result_element_done, "1"),
            Pair(R.drawable.result_element_done, "2"),
            Pair(R.drawable.result_element_done, "3"),
            Pair(R.drawable.result_element_fail, "4"),
            Pair(R.drawable.result_element_done, "5"),
            Pair(R.drawable.result_element_fail, "6"),
            Pair(R.drawable.result_element_fail, "7"),
            Pair(R.drawable.result_element_done, "8"),
            Pair(R.drawable.result_element_done, "9"),
            Pair(R.drawable.result_element_done, "10"),
        )

        val adapter = ResultListAdapter(requireContext(), data)
        gridView.adapter = adapter

        binding.end.setOnClickListener{
            val intent = Intent(requireActivity(), PagesActivity::class.java)
            startActivity(intent)
        }


        return binding.root
    }
}