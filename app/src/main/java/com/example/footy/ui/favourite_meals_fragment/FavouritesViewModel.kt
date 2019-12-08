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

    private val _navigateToOfflineRecipeFragment = MutableLiveData<String>()
    val navigateToOfflineRecipeFragment: LiveData<String>
        get() {
            return _navigateToOfflineRecipeFragment
        }

    private val _favouriteListSize = MutableLiveData<Int>()
    val favouriteListSize: LiveData<Int>
        get() {
            return _favouriteListSize
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
            val allIngredients = database.getAllIngredients()
            _favouriteMeals.postValue(allIngredients)
            _favouriteListSize.postValue(allIngredients.size)
        }

    }

    fun onRecipeClicked(ClickedMealID: String) {
        _navigateToOfflineRecipeFragment.value = ClickedMealID
    }

    fun navigationToRecipeFragmentComplete() {
        _navigateToOfflineRecipeFragment.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
