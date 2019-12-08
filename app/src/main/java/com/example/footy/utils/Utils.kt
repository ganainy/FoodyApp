package com.example.footy.utils

import com.example.footy.network.Ingredient

fun filterIngredientsList(ingredient: Ingredient): MutableList<String> {
    val ingredientsList = mutableListOf<String>()


    if (ingredient.strIngredient1 != "") ingredientsList.add("•" + ingredient.strIngredient1 + " : " + ingredient.strMeasure1)
    if (ingredient.strIngredient2 != "") ingredientsList.add("•" + ingredient.strIngredient2 + " : " + ingredient.strMeasure2)
    if (ingredient.strIngredient3 != "") ingredientsList.add("•" + ingredient.strIngredient3 + " : " + ingredient.strMeasure3)
    if (ingredient.strIngredient4 != "") ingredientsList.add("•" + ingredient.strIngredient4 + " : " + ingredient.strMeasure4)
    if (ingredient.strIngredient5 != "") ingredientsList.add("•" + ingredient.strIngredient5 + " : " + ingredient.strMeasure5)
    if (ingredient.strIngredient6 != "") ingredientsList.add("•" + ingredient.strIngredient6 + " : " + ingredient.strMeasure6)
    if (ingredient.strIngredient7 != "") ingredientsList.add("•" + ingredient.strIngredient7 + " : " + ingredient.strMeasure7)
    if (ingredient.strIngredient8 != "") ingredientsList.add("•" + ingredient.strIngredient8 + " : " + ingredient.strMeasure8)
    if (ingredient.strIngredient9 != "") ingredientsList.add("•" + ingredient.strIngredient9 + " : " + ingredient.strMeasure9)
    if (ingredient.strIngredient10 != "") ingredientsList.add("•" + ingredient.strIngredient10 + " : " + ingredient.strMeasure10)
    if (ingredient.strIngredient11 != "") ingredientsList.add("•" + ingredient.strIngredient11 + " : " + ingredient.strMeasure11)
    if (ingredient.strIngredient12 != "") ingredientsList.add("•" + ingredient.strIngredient12 + " : " + ingredient.strMeasure12)
    if (ingredient.strIngredient13 != "") ingredientsList.add("•" + ingredient.strIngredient13 + " : " + ingredient.strMeasure13)
    if (ingredient.strIngredient14 != "") ingredientsList.add("•" + ingredient.strIngredient14 + " : " + ingredient.strMeasure14)
    if (ingredient.strIngredient15 != "") ingredientsList.add("•" + ingredient.strIngredient15 + " : " + ingredient.strMeasure15)
    if (ingredient.strIngredient16 != "") ingredientsList.add("•" + ingredient.strIngredient16 + " : " + ingredient.strMeasure16)
    if (ingredient.strIngredient17 != "") ingredientsList.add("•" + ingredient.strIngredient17 + " : " + ingredient.strMeasure17)
    if (ingredient.strIngredient18 != "") ingredientsList.add("•" + ingredient.strIngredient18 + " : " + ingredient.strMeasure18)
    if (ingredient.strIngredient19 != "") ingredientsList.add("•" + ingredient.strIngredient19 + " : " + ingredient.strMeasure19)
    if (ingredient.strIngredient20 != "") ingredientsList.add("•" + ingredient.strIngredient20 + " : " + ingredient.strMeasure20)

    return ingredientsList
}