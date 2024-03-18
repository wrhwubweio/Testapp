package com.example.testapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "cocktails")
@TypeConverters(ListStringConverter::class)
data class CocktailEntity(
    @PrimaryKey val id: String,
    val name: String,
    val instructions: String,
    val imageUrl: String,
    val ingredients: List<String>
)