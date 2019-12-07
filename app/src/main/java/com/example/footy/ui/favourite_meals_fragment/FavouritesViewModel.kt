package com.example.footy.ui.favourite_meals_fragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.footy.database.IngredientDatabaseDao
import com.example.footy.network.Ingredient
import kotlinx.coroutines.*

class FavouritesViewModel(val database: IngredientDatabaseDao, application: Application) :
    ViewModel() {


    private val _favouriteMeals = MutableLiveData<List<Ingredient>>()
    val favoriteMeals: LiveData<List<Ingredient>>
        get() {
            return _favouriteMeals
        }

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        coroutineScope.launch {
            getFavouriteMeals()
        }
    }

    private suspend fun getFavouriteMeals() {

        withContext(Dispatchers.IO) {
            _favouriteMeals.postValue(database.getAllIngredients())
        }

    }

}
