package com.example.testapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
    @GET("random.php")
    fun getRandomCocktails(@Query("limit") limit: Int): Call<CocktailResponse>
}