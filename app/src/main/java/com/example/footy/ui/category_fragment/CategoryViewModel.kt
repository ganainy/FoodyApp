package com.example.footy.ui.category_fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.footy.network.Category
import com.example.footy.network.Meal
import com.example.footy.network.MealApi
import com.example.footy.ui.home_fragment.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class CategoryViewModel(category: Category, app: Application) : AndroidViewModel(app) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _mealLoadState: MutableLiveData<HomeViewModel.State> = MutableLiveData()
    val mealLoadState: LiveData<HomeViewModel.State>
        get() {
            return _mealLoadState
        }


    private val _mealList: MutableLiveData<List<Meal>> = MutableLiveData()
    val mealList: LiveData<List<Meal>>
        get() {
            return _mealList
        }

    private val _selectedCategory: MutableLiveData<Category> = MutableLiveData()
    val seletedCategory: LiveData<Category>
        get() {
            return _selectedCategory
        }


    // Initialize the _selectedCategory MutableLiveData
    init {
        _selectedCategory.value = category
        getMealsOfCategory(category.categoryName)
    }


    private fun getMealsOfCategory(categoryName: String) {
        _mealLoadState.value = HomeViewModel.State.LOADING
        //must be in coroutine scope to use deffered(special type of job)
        coroutineScope.launch {
            try {
                val resultMealList = MealApi.retrofitService.getMealsOfCategoryAsync(categoryName)
                    .await() //waiting for result without blocking ui thread
                _mealList.value = resultMealList.meals
                _mealLoadState.value = HomeViewModel.State.SUCCESS
                Log.i("mTag", "success")
            } catch (t: Throwable) {
                Log.i("mTag", "${t.message}$categoryName")
                _mealLoadState.value = HomeViewModel.State.FAILED
            }


        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
