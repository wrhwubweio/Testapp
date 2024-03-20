package com.example.testapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.mycocktailapp.ui.theme.TestappTheme
import com.example.testapp.api.MainApi
import com.example.testapp.database.QuestionDatabase
import com.example.testapp.views.TestViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AppActivity : AppCompatActivity() {
    private val testView by viewModels<TestViewModel>()

    private val questionDao: QuestionDao by lazy {
        QuestionDatabase.getInstance(this).questionDao()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val questionApi: MainApi by lazy { retrofit.create(MainApi::class.java) }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint(
        "UnusedMaterial3ScaffoldPaddingParameter",
        "UnusedMaterialScaffoldPaddingParameter"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestappTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val coroutineScope = rememberCoroutineScope()
                val constraints = Constraints.Builder()
                    .build()

                val workRequest = OneTimeWorkRequestBuilder<QuestionUpdateWorker>()
                    .setConstraints(constraints)
                    .build()

                WorkManager.getInstance(this).enqueue(workRequest)

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                val currentRoute =
                                    navController.currentBackStackEntryAsState().value?.destination?.route
                                val screenTitle = getTitleForRoute(currentRoute)
                                Text(
                                    text = screenTitle
                                        ?: stringResource(id = androidx.compose.ui.R.string.navigation_menu),
                                    style = TextStyle(color = Color.White, fontSize = 20.sp)
                                )
                            },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            scaffoldState.drawerState.open()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Open Navigation Drawer",
                                        tint = Color.White
                                    )
                                }
                            },
                            colors = topAppBarColors(Color(0xFF6B6E92))
                        )
                    },
                    bottomBar = {
                        BottomAppBar(
                            backgroundColor = Color(0xFF64688A),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                BottomNavigationIcon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Список тестов",
                                    onClick = { navController.navigate("fragment_main") }
                                )

                                BottomNavigationIcon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Случайный тест",
                                    onClick = { navController.navigate("fragment_test") }
                                )
                            }
                        }
                    },
                    scaffoldState = scaffoldState,
                    content = {
                        Column(modifier = Modifier.padding(bottom = 55.dp)) {
                            Navigation(navController = navController)
                        }
                    },
                    drawerBackgroundColor = Color(0xFF555875),
                )
            }
        }
    }

    @Composable
    fun Navigation(navController: NavHostController) {

        NavHost(navController = navController, startDestination = "fragment_login") {
            composable("fragment_login") {
                LoginScreen(navController = navController)
            }
            composable("fragment_main") {
                MainScreen(navController = navController)
            }
            composable("fragment_test") {
                TestScreen(navController = navController, questionDao, this@AppActivity)
            }
        }
    }

    @Composable
    fun BottomNavigationIcon(
        imageVector: ImageVector,
        contentDescription: String?,
        onClick: () -> Unit
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = Color.White
            )
        }
    }
}

@Composable
fun getTitleForRoute(route: String?): String? {
    return when (route) {
        "fragment_main" -> "Список тестов"
        "fragment_test" -> "Тест №"
        else -> null
    }
}