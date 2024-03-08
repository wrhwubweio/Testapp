package com.example.testapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.testapp.databinding.TestPageBinding

class TestPage : Fragment() {
    private lateinit var binding: TestPageBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TestPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            binding.nameTest.text = "Тест №" + (bundle.getLong("id_test") + 1).toString()
        }

        binding.endTest.setOnClickListener{
            findNavController().navigate(R.id.action_testPage_to_mainPage)
        }
    }
}