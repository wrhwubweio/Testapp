package com.example.testapp.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.R
import com.example.testapp.model.Item
import kotlin.random.Random

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

    fun genList() : List<Item>{
        val itemsList = (0..29).map { index ->
            val pic = when (Random.nextInt(3)) {
                0 -> R.drawable.circle
                1 -> R.drawable.circle_done
                else -> R.drawable.fail
            }
            Item(pic, "Test ${index + 1}", true)
        }
        return itemsList
    }
}