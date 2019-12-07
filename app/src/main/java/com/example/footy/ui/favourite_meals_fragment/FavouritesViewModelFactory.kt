package com.example.footy.ui.favourite_meals_fragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.footy.database.IngredientDatabaseDao

class FavouritesViewModelFactory(
    private val database: IngredientDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {


    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            return FavouritesViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}