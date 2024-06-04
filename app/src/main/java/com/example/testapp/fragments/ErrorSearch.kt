package com.example.testapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.databinding.FragmentErrorSearchBinding

class ErrorSearch : Fragment() {

    private lateinit var binding: FragmentErrorSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentErrorSearchBinding.inflate(inflater, container, false)

        binding.refreshButton.setOnClickListener{
            Library.getInstance().search()
        }

        return binding.root
    }
}