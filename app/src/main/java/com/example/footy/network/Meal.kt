package com.example.footy.network


data class MealsOfCertainCategory(
    val meals: List<Meal>
)

data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)