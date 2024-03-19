package com.example.testapp

import androidx.room.Room
import com.example.testapp.api.MainApi
import com.example.testapp.database.QuestionDatabase
import com.example.testapp.views.LoginViewModel
import com.example.testapp.views.MainViewModel
import com.example.testapp.views.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    // Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://opentdb.com/") // URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // MainApi
    single {
        val retrofit: Retrofit = get()
        retrofit.create(MainApi::class.java)
    }
    // Room Database
    single {
        Room.databaseBuilder(
            get(),
            QuestionDatabase::class.java,
            "questions_database"
        ).build()
    }

    // DAO
    single { get<QuestionDatabase>().questionDao() }

    // ViewModel
    viewModel { LoginViewModel() }
    viewModel { MainViewModel() }
    viewModel { TestViewModel() }
}
