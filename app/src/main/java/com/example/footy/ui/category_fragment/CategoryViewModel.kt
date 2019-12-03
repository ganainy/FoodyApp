package com.example.footy.ui.category_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.footy.network.Category


class CategoryViewModel(category: Category, app: Application) : AndroidViewModel(app) {

    private val _selectedCategory: MutableLiveData<Category> = MutableLiveData()
    val seletedCategory: LiveData<Category>
        get() {
            return _selectedCategory
        }


    // Initialize the _selectedCategory MutableLiveData
    init {
        _selectedCategory.value = category
    }


}
