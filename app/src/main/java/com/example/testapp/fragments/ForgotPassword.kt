package com.example.testapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.activities.PagesActivity
import com.example.testapp.databinding.FragmentForgotPasswordBinding


class ForgotPassword : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        binding.login.setOnClickListener{
            val intent = Intent(requireActivity(), PagesActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}