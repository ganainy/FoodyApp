package com.example.footy.ui.offline_recipe_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.footy.database.IngredientDatabaseDao

class OfflineRecipeViewModelFactory(
    private val database: IngredientDatabaseDao,
    private val mealId: String
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OfflineRecipeViewModel::class.java)) {
            return OfflineRecipeViewModel(database, mealId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}