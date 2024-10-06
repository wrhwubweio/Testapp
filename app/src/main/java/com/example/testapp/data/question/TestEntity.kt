package com.example.testapp.data.question

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(tableName = "testes")
@TypeConverters(QuestionConverter::class)
data class TestEntity(
    @PrimaryKey val id: Int,
    val categoryId: Int,
    var percentage: Double,
    val name: String,
    val description: String,
    val group: String,
    val count: Int,
    var questions: List<Question>?
)
