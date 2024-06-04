package com.example.testapp.data.question

import androidx.room.TypeConverter

class ListStringConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(data: String): List<String> {
        return data.split(",").map { it.trim() }
    }
}