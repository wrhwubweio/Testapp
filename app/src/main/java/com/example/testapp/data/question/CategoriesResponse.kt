package com.example.testapp.data.question

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CategoriesResponse (
    @SerializedName("trivia_categories")
    @Expose
    val results: List<Categories>
)