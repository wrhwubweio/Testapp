package com.example.testapp.api

import com.example.testapp.model.QuestionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {
    @GET("api.php?")
    fun getQuiz(@Query("amount") amount: Int, @Query("category") category: Int, @Query("type") type: String): Call<QuestionResponse>
}