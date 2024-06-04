package com.example.testapp.data.question

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuestionConverter {
    @TypeConverter
    fun fromQuestions(questions: List<Question>): String {
        return Gson().toJson(questions)
    }

    @TypeConverter
    fun toQuestions(json: String): List<Question> {
        val type = object : TypeToken<List<Question>>() {}.type
        return Gson().fromJson(json, type)
    }
}