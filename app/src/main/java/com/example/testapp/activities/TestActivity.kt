package com.example.testapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.testapp.R
import com.example.testapp.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        binding = ActivityTestBinding.inflate(layoutInflater)

        val bundle = intent.extras

        if(bundle != null){
            bundle.getInt("id_test")
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_graph_test) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.testPage2, bundle)
        }

        setContentView(binding.root)
    }
}