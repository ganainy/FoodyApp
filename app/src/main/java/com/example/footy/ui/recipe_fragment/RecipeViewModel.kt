package com.example.footy.ui.recipe_fragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.footy.network.MealApi
import com.example.footy.network.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RecipeViewModel(id: Int, application: Application) : ViewModel() {

    val viewModelJob = Job()
    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _recipe: MutableLiveData<Recipe> = MutableLiveData()
    val recipe: LiveData<Recipe>
        get() {
            return _recipe
        }


    private val _ytLink: MutableLiveData<String> = MutableLiveData()
    val ytLink: LiveData<String>
        get() {
            return _ytLink
        }


    init {
        getSpecificRecipe(id)
    }

    private fun getSpecificRecipe(id: Int) {

        coroutineScope.launch {
            try {
                val recipe = MealApi.retrofitService.getRecipeAsync(id).await()
                _recipe.value = recipe
            } catch (t: Throwable) {
                println("RecipeViewModel.getSpecificRecipe:${t.message}")
            }
        }
    }


    fun openYoutube(youtubeLink: String) {
        _ytLink.value = youtubeLink
    }
}
