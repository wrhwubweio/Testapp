package com.example.testapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestViewModel : ViewModel() {

    private val _navigateToMainPageFragment = MutableLiveData<Boolean>()
    private val cocktailDao: CocktailDao by lazy { CocktailDatabase.getInstance(appContext).cocktailDao() }

    val navigateToMainPageFragment: LiveData<Boolean>
        get() = _navigateToMainPageFragment

    fun onButtonEnd(){
        _navigateToMainPageFragment.value = true
    }

    fun onMainPageNavigated(){
        _navigateToMainPageFragment.value = false
    }

    fun loadDataFromApi(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://500-quiz-api.p.rapidapi.com/api")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val quizApi = retrofit.create(CocktailApi::class.java)

        GlobalScope.launch(Dispatchers.IO){
            val currentCount = cock
        }
    }


}