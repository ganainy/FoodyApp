package com.example.footy.ui.recipe_fragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.footy.database.IngredientDatabaseDao
import com.example.footy.network.Ingredient
import com.example.footy.network.MealApi
import com.example.footy.network.Recipe
import kotlinx.coroutines.*

class RecipeViewModel(
    id: Int,
    application: Application,
    database: IngredientDatabaseDao
) : ViewModel() {

    val viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    lateinit var mDatabase: IngredientDatabaseDao

    private val _recipe: MutableLiveData<Recipe> = MutableLiveData()
    val recipe: LiveData<Recipe>
        get() {
            return _recipe
        }


    private val _duplicate: MutableLiveData<String> = MutableLiveData()
    val duplicate: LiveData<String>
        get() {
            return _duplicate
        }

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite: LiveData<Boolean>
        get() {
            return _isFavorite
        }


    private val _ytLink: MutableLiveData<String> = MutableLiveData()
    val ytLink: LiveData<String>
        get() {
            return _ytLink
        }


    init {
        getSpecificRecipe(id)
        mDatabase = database
    }

    private fun getSpecificRecipe(mealBackendId: Int) {

        coroutineScope.launch {
            try {
                val recipe = MealApi.retrofitService.getRecipeAsync(mealBackendId).await()
                getFavoriteStatus(recipe.meals[0].strMeal) //after loading meal check if it is in favorites
                _recipe.value = recipe
            } catch (t: Throwable) {
                println("RecipeViewModel.getSpecificRecipe:${t.message}")
            }
        }
    }


    private suspend fun getFavoriteStatus(strMeal: String?) {

        withContext(Dispatchers.IO)
        {
            val favoriteIngredientsNames = mDatabase.getFavoriteIngredientsNames()
            for (name in favoriteIngredientsNames) {
                if (name == strMeal) _isFavorite.postValue(true)
            }
        }
    }


    fun addOrDeleteToDB(ingredient: Ingredient): Unit {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                val favoriteIngredientsNames = mDatabase.getFavoriteIngredientsNames()
                if (favoriteIngredientsNames.contains(ingredient.strMeal)) {
                    //meal already in database
                    mDatabase.delete(ingredient.strMeal)
                    _isFavorite.postValue(false)
                } else {
                    mDatabase.insert(ingredient)
                    _isFavorite.postValue(true)
                }

            }
        }
    }


    fun openYoutube(youtubeLink: String) {
        _ytLink.value = youtubeLink
    }
}
