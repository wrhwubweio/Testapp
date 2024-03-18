package com.example.testapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testapp.databinding.TestPageBinding

class TestPage : Fragment() {
    private lateinit var binding: TestPageBinding;
    private lateinit var viewModel: TestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TestPageBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)

        binding.nameTest.text = "Тест №" + (getArguments()?.getLong("id_test")?.plus(1)).toString()
        binding.endTest.setOnClickListener{
           viewModel.onButtonEnd()
        }

        viewModel.navigateToMainPageFragment.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate){
                findNavController().navigate(R.id.action_testPage_to_mainPage)
                viewModel.onMainPageNavigated()
            }
        })

        return binding.root
    }

}