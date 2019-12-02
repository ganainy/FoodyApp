package com.example.footy.ui.home_fragment.categories_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.footy.R
import com.example.footy.network.Category

@BindingAdapter("categoryImage")
fun ImageView.setCategoryImage(item: Category?) {
    item?.let {
        setImageResource(R.drawable.ic_broken_image)
    }
}