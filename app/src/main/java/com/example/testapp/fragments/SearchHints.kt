package com.example.testapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.adapters.CategoryAdapter
import com.example.testapp.data.question.CategoriesResponse
import com.example.testapp.data.question.MainApi
import com.example.testapp.databinding.FragmentSearchHintsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchHints : Fragment() {

    private lateinit var binding: FragmentSearchHintsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchHintsBinding.inflate(inflater, container, false)
        binding.listResult.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val res = arguments?.getString("search_input").toString()
        loadDataQuestions(res)
    }

    private fun loadDataQuestions(input: String){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val quizApi = retrofit.create(MainApi::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val call = quizApi.getCategories()
            call.enqueue(object : Callback<CategoriesResponse> {
                override fun onResponse(
                    call: Call<CategoriesResponse>,
                    response: Response<CategoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        val categoriesResponse = response.body()
                        val categories = categoriesResponse?.results
                        val categoryByName = categories?.filter { it.name.toLowerCase().contains(input)}

                        if(categoryByName?.isNotEmpty() == true){
                            binding.listResult.adapter = CategoryAdapter(categoryByName)
                        }
                        else{
                            findNavController().navigate(R.id.emptySearch)
                        }
                    }
                }

                override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                    findNavController().navigate(R.id.errorSearch)
                }
            })

        }
    }
}