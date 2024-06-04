package com.example.testapp.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentLibraryBinding

class Library : Fragment() {
    private lateinit var binding: FragmentLibraryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        binding.clearSearch.isVisible = false

        binding.searchInput.setOnKeyListener(object : View.OnKeyListener {

            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    hideKeyboardFrom(requireContext(), binding.root)
                    return true
                }
                return false
            }
        })


        binding.searchInput.doOnTextChanged { text, start, before, count ->
            if (text?.length != 0) {
                val bundle = Bundle()
                bundle.putString("search_input", text?.toString())
                binding.navHostFragmentContainer.findNavController()
                    .navigate(R.id.searchHints, bundle)
                binding.clearSearch.isVisible = true
            }
            else{
                binding.clearSearch.isVisible = false
            }
        }


        binding.searchInput.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                binding.navHostFragmentContainer.findNavController()
                    .navigate(R.id.categoryLibrary)
            }
        }

        binding.clearSearch.setOnClickListener{
            clear()
        }

        return binding.root
    }



    fun clear(){
        binding.searchInput.text.clear()
        hideKeyboardFrom(requireContext(), binding.root)
        binding.clearSearch.isVisible = false
        binding.navHostFragmentContainer.findNavController()
            .navigate(R.id.categoryLibrary)
        binding.searchInput.clearFocus()
    }


    fun hideKeyboardFrom(context: Context, view: View?) {
        val imm =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    public fun search(){
        val bundle = Bundle()
        bundle.putString("search_input", binding.searchInput.text.toString())
        binding.navHostFragmentContainer.findNavController()
            .navigate(R.id.searchHints, bundle)
        binding.clearSearch.isVisible = true
    }

    companion object {
        private var instance: Library? = null
        fun getInstance(): Library {
            if (instance == null) {
                instance = Library()
            }
            return instance!!
        }
    }
}