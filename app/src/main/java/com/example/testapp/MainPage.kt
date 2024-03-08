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
import androidx.navigation.fragment.findNavController
import com.example.testapp.databinding.FragmentMainPageBinding
import java.util.Random

class MainPage : Fragment() {


    var complete = java.util.ArrayList<Long>()
    private lateinit var binding: FragmentMainPageBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            binding.outText.text = bundle.getString("name")
        }

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
        val result = Bundle()
        listView.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l ->
                result.putLong("id_test", l)
                parentFragmentManager.setFragmentResult(
                    "requestKey", result
                )
                findNavController().navigate(R.id.action_mainPage_to_testPage)
            }
        binding.quit.setOnClickListener{
            findNavController().navigate(R.id.action_mainPage_to_login)
        }
    }

    private fun OutInfo(text: String, outToast: Boolean) {
        Log.d("MINE", text)
        if (!outToast) return
        val toast = Toast.makeText(
            requireActivity().applicationContext,
            text, Toast.LENGTH_SHORT
        )
        toast.show()
    }
}