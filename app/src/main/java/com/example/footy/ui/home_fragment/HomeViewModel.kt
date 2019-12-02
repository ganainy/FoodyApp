package com.example.footy.ui.home_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.footy.network.MealApi
import com.example.footy.network.MealCategories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _categories = MutableLiveData<MealCategories>()

    val categories: LiveData<MealCategories>
        get() = _categories


    init {
        getCategories()
    }

    private fun getCategories() {

        //must be in coroutine scope to use deffered(special type of job)
        coroutineScope.launch {
            try {
                val mealCategories = MealApi.retrofitService.getCategoriesAsync()
                    .await() //waiting for result without blocking ui thread
                _categories.value = mealCategories
            } catch (t: Throwable) {
                Log.i(this.javaClass.name, "${t.message}")
            }

            //   _categories.value = response.body()


        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onCategoryClicked(categoryId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
