package com.example.footy.ui.recipe_fragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.footy.database.IngredientDatabaseDao
import com.example.footy.network.Ingredient
import com.example.footy.network.MealApi
import com.example.footy.network.Recipe
import com.example.footy.ui.list_of_categories_fragment.HomeViewModel
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

    private val _recipeLoadState: MutableLiveData<HomeViewModel.State> = MutableLiveData()
    val recipeLoadState: LiveData<HomeViewModel.State>
        get() {
            return _recipeLoadState
        }

    private val _toast: MutableLiveData<String> = MutableLiveData()
    val toast: LiveData<String>
        get() {
            return _toast
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

    fun getSpecificRecipe(mealBackendId: Int) {

        if (_recipe.value != null) return

        _recipeLoadState.value = HomeViewModel.State.LOADING
        coroutineScope.launch {
            try {
                val recipe = MealApi.retrofitService.getRecipeAsync(mealBackendId).await()
                getFavoriteStatus(recipe.meals[0].strMeal) //after loading meal check if it is in favorites
                _recipe.value = recipe
                _recipeLoadState.value = HomeViewModel.State.SUCCESS
            } catch (t: Throwable) {
                println("RecipeViewModel.getSpecificRecipe:${t.message}")
                _recipeLoadState.value = HomeViewModel.State.FAILED
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
                    mDatabase.deleteRecipeById(ingredient.idMeal)
                    _isFavorite.postValue(false)
                    _toast.postValue("Removed ${ingredient.strMeal} from your favourites.")
                } else {
                    mDatabase.insert(ingredient)
                    _isFavorite.postValue(true)
                    _toast.postValue("Added ${ingredient.strMeal} to your favourites.")
                }

            }
        }
    }


    fun openYoutube(youtubeLink: String) {
        _ytLink.value = youtubeLink
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
