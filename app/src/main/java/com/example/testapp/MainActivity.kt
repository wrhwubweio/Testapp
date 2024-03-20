package com.example.testapp

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycocktailapp.ui.theme.TestappTheme
import com.example.testapp.database.QuestionDatabase

class MainActivity : AppCompatActivity() {
    private val questionDao: QuestionDao by lazy {
        this.let {
            QuestionDatabase.getInstance(it).questionDao()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        checkInternetPermission()
        setContent {
            TestappTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "fragment_login") {
                        composable("fragment_login") {
                            LoginScreen(navController = navController)
                        }
                        composable("fragment_main") {
                            MainScreen(navController = navController)
                        }
                        composable("fragment_test") {
                            TestScreen(navController = navController, questionDao, this@MainActivity)
                        }
                    }
                }
            }
        }
    }

    private fun checkInternetPermission(): Boolean {
        val permission = android.Manifest.permission.INTERNET
        return if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 2)
            false
        }
    }
}