package com.example.testapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        binding.listView.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l ->
                viewModel.setNumTest(l)
                viewModel.onButtonTest()
            }

        viewModel.navigateToTestFragment.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate){
                val result = Bundle()
                viewModel.numTest.value?.let { result.putLong("id_test", it) }
                val intent = Intent(requireActivity(), TestActivity::class.java)
                startActivity(intent)
                viewModel.onTestNavigated()
            }
        })

        viewModel.navigateToLoginFragment.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate){

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
            var precent = "-%"

            when (rand) {
                0 -> pic = R.drawable.test
                1 -> pic = R.drawable.test_done
                2 -> pic = R.drawable.test_fail
            }

            when (rand) {
                0 -> precent = "-%"
                1 -> precent = (50 + Random().nextInt(50)).toString() + "%"
                2 -> precent = Random().nextInt(50).toString() + "%"
            }

            val index = i + 1
            items.add(Item(pic, "Тест $index", precent))
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