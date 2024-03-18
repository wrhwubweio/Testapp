package com.example.testapp

data class Cocktail(
    val idDrink: String,
    val strDrink: String,
    val strInstructions: String,
    val strDrinkThumb: String, // URL изображения
    val ingredients: List<String>
)