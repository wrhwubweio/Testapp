package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.databinding.FragmentLibraryBinding

class Library : Fragment() {

    private lateinit var binding: FragmentLibraryBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)

        val gridView = binding.libraryGrid
        gridView.numColumns = 2

        val data: List<Pair<Int, String>> = listOf(
            Pair(R.drawable.history, "История"),
            Pair(R.drawable.prog, "Программирование"),
            Pair(R.drawable.eng, "Английский"),
            Pair(R.drawable.math, "Математика"),
            Pair(R.drawable.physics, "Физика"),
            Pair(R.drawable.bio, "Биология"),
        )

        val adapter = LibraryListAdapter(requireContext(), data)
        gridView.adapter = adapter


        return binding.root
    }
}