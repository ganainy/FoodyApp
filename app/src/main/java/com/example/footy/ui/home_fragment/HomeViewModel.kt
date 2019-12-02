package com.example.footy.ui.home_fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.footy.network.MealApi
import com.example.footy.network.MealCategories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _categories = MutableLiveData<MealCategories>()

    // The external immutable LiveData for the response String
    val categories: LiveData<MealCategories>
        get() = _categories


    init {
        getCategories()

    }

    private fun getCategories() {

        MealApi.retrofitService.getCategories().enqueue(object : Callback<MealCategories> {
            override fun onFailure(call: Call<MealCategories>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<MealCategories>,
                response: Response<MealCategories>
            ) {
                _categories.value = response.body()

            }

        })
    }
}
