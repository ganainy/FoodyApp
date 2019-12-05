package com.example.footy.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class MealsOfCertainCategory(
    val meals: List<Meal>
)

@Parcelize
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
) : Parcelable