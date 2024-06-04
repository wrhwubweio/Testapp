package com.example.testapp.data.question

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "questions")
@TypeConverters(ListStringConverter::class)
data class QuestionEntity(
    @PrimaryKey val id: Int,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)