package com.example.footy.network

import com.squareup.moshi.Json

data class MealCategories(val categories: List<Category>)


data class Category(
    val idCategory: String,
    @Json(name = "strCategory") val categoryName: String,
    val strCategoryDescription: String,
    @Json(name = "strCategoryThumb") val categoryImageUrl: String
)