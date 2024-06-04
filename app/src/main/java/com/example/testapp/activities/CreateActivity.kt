package com.example.testapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.testapp.R
import com.example.testapp.databinding.ActivityCreateBinding

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        binding = ActivityCreateBinding.inflate(layoutInflater)

        val bundle = intent.extras

        if(bundle != null){
            bundle.getInt("test_index")
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.questionsCreate, bundle)
        }

        setContentView(binding.root)
    }

}