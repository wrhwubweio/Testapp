package com.example.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.testapp.databinding.ActivityPagesBinding


class PagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()

        binding = ActivityPagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.include.libraryButton.setOnClickListener {
            navController.navigate(R.id.library)
        }

        binding.include.createButton.setOnClickListener {
            navController.navigate(R.id.mainPage)
        }

        binding.include.profileButton.setOnClickListener {
            navController.navigate(R.id.profile)
        }

        binding.include.myTestButton.setOnClickListener {
            navController.navigate(R.id.mainPage)
        }
    }
}