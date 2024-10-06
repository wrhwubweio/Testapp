package com.example.testapp.fragments

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
import com.example.testapp.R
import com.example.testapp.activities.TestActivity
import com.example.testapp.adapters.ListViewAdapter
import com.example.testapp.data.question.Item
import com.example.testapp.data.question.MyTestesDB
import com.example.testapp.data.question.TestDao
import com.example.testapp.databinding.FragmentMainPageBinding
import com.example.testapp.view_models.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainPage : Fragment() {
    private lateinit var binding: FragmentMainPageBinding;
    private lateinit var viewModel: MainViewModel
    private val testDao: TestDao by lazy { context?.let { MyTestesDB.getInstance(it).testDao() }!! }
    private var adapter: ListViewAdapter? = null
    public var category = 23
    public var idTest = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        instance = this

        binding.listView.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l ->
                viewModel.setNumTest(l)
                viewModel.onButtonTest()
            }

        viewModel.navigateToTestFragment.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate){
                val result = Bundle()
                val index = viewModel.numTest.value
                if (index != null) {
                    category = adapter?.getItem(index.toInt())?.getCategory() ?: -1
                    idTest = index.toInt()
                }
                val intent = Intent(requireActivity(), TestActivity::class.java)
                startActivity(intent, result)
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

        GlobalScope.launch(Dispatchers.IO) {
            val dataTestes = testDao.getTestes()
            withContext(Dispatchers.Main) {

                for (i in 0 .. dataTestes.size - 1) {
                    var pic = R.drawable.test
                    var precent = dataTestes[i].percentage.toString() + "%"

                    when {
                        dataTestes[i].percentage < 0 -> pic = R.drawable.test
                        dataTestes[i].percentage < 50 -> pic = R.drawable.test_fail
                        dataTestes[i].percentage <= 100 -> pic = R.drawable.test_done
                    }

                    if(dataTestes[i].percentage < 0){
                        precent = "-%"
                    }

                    items.add(Item(pic, dataTestes[i].name, precent, dataTestes[i].categoryId))
                }
                adapter = ListViewAdapter(view.context, 0,0, items)
                listView.setAdapter(adapter)
            }
        }
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

    companion object {
        private var instance: MainPage? = null
        fun getInstance(): MainPage {
            if (instance == null) {
                instance = MainPage()
            }
            return instance!!
        }
    }
}