package com.example.testapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class QuestionResponse(
    @SerializedName("results")
    @Expose
    val results: List<Question>
)