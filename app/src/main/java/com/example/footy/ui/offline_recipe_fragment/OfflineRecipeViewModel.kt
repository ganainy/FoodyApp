package com.example.footy.ui.offline_recipe_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.footy.database.IngredientDatabaseDao
import com.example.footy.network.Ingredient
import kotlinx.coroutines.*

class OfflineRecipeViewModel(
    database: IngredientDatabaseDao,
    mealId: String
) : ViewModel() {

    private var mMealId: String
    private var mDatabase: IngredientDatabaseDao
    var viewmodelJob = Job()
    var coroutineScope = CoroutineScope(viewmodelJob + Dispatchers.Main)

    private val _ingredient: MutableLiveData<Ingredient> = MutableLiveData()
    val ingredient: LiveData<Ingredient>
        get() {
            return _ingredient
        }

    private val _deleteDone: MutableLiveData<Boolean> = MutableLiveData()
    val deleteDone: LiveData<Boolean>
        get() {
            return _deleteDone
        }

    init {
        getRecipeFromDB(mealId, database)
        mDatabase = database
        mMealId = mealId
    }

    fun getRecipeFromDB(
        mealId: String,
        dataSource: IngredientDatabaseDao
    ) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                _ingredient.postValue(dataSource.getRecipeByID(mealId))
            }
        }
    }


    fun deleteFromDB(): Unit {

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                mDatabase.deleteRecipeById(mMealId)
                _deleteDone.postValue(true)
            }
        }

    }

    fun navigationComplete() {
        _deleteDone.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewmodelJob.cancel()
    }
}
