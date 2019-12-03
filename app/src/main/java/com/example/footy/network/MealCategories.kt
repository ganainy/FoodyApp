package com.example.footy.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class MealCategories(val categories: List<Category>)

@Parcelize
data class Category(
    val idCategory: String,
    @Json(name = "strCategory") val categoryName: String,
    val strCategoryDescription: String,
    @Json(name = "strCategoryThumb") val categoryImageUrl: String
) : Parcelable