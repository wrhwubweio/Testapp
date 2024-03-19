package com.example.testapp.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {

    private val _navigateToMainPageFragment = MutableLiveData<Boolean>()

    val navigateToMainPageFragment: LiveData<Boolean>
        get() = _navigateToMainPageFragment

    fun onButtonEnd(){
        _navigateToMainPageFragment.value = true
    }

    fun onMainPageNavigated(){
        _navigateToMainPageFragment.value = false
    }
}