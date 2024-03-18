package com.example.testapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.testapp.databinding.FragmentMainPageBinding
import java.util.Random

class MainPage : Fragment() {
    private lateinit var binding: FragmentMainPageBinding;
    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.outText.text =  getArguments()?.getString("name")

        binding.listView.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l ->
                viewModel.setNumTest(l)
                viewModel.onButtonTest()
            }

        binding.quit.setOnClickListener{
            viewModel.onButtonBack()
        }

        viewModel.navigateToTestFragment.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate){
                val result = Bundle()
                viewModel.numTest.value?.let { result.putLong("id_test", it) }
                findNavController().navigate(R.id.action_mainPage_to_testPage, result)
                viewModel.onTestNavigated()
            }
        })

        viewModel.navigateToLoginFragment.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate){
                findNavController().navigate(R.id.action_mainPage_to_login)
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        generateList(view)
    }


    private fun generateList(view: View){
        val listView = binding.listView
        val items = ArrayList<Item>()
        for (i in 0 .. 29) {
            var pic = R.drawable.circle
            val rand = Random().nextInt(3)
            when (rand) {
                0 -> pic = R.drawable.circle
                1 -> pic = R.drawable.circle_done
                2 -> pic = R.drawable.fail
            }
            val index = i + 1
            items.add(Item(pic, "Test $index", true))
        }
        val adapter = ListViewAdapter(view.context, 0,0, items)
        listView.setAdapter(adapter)
    }

    private fun outInfo(text: String, outToast: Boolean) {
        Log.d("MINE", text)
        if (!outToast) return
        val toast = Toast.makeText(
            requireActivity().applicationContext,
            text, Toast.LENGTH_SHORT
        )
        toast.show()
    }
}