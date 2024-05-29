package com.example.testapp.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _navigateToLoginFragment = MutableLiveData<Boolean>()
    private val _navigateToTestFragment = MutableLiveData<Boolean>()
    private val _numTest = MutableLiveData<Long>()

    val navigateToLoginFragment: LiveData<Boolean>
        get() = _navigateToLoginFragment
    val navigateToTestFragment: LiveData<Boolean>
        get() = _navigateToTestFragment
    val numTest: LiveData<Long>
        get() = _numTest

    fun onButtonBack(){
        _navigateToLoginFragment.value = true
    }

    fun onButtonTest(){
        _navigateToTestFragment.value = true
    }

    fun setNumTest(num: Long){
        _numTest.value = num
    }

    fun onLoginNavigated(){
        _navigateToLoginFragment.value = false
    }

    fun onTestNavigated(){
        _navigateToTestFragment.value = false
    }
}