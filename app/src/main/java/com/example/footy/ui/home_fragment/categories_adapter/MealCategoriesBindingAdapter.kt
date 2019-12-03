package com.example.footy.ui.home_fragment.categories_adapter

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.footy.R
import com.example.footy.network.Category
import com.example.footy.ui.home_fragment.HomeViewModel

@BindingAdapter("categoryImage")
fun setCategoryImage(imageView: ImageView, item: Category?) {
    item?.let {
        val imageUri = it.categoryImageUrl.toUri()
        Glide.with(imageView.context)
            .load(imageUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imageView)
    }
}


@BindingAdapter("setLoadingState")
fun ImageView.setTheLoadingState(state: HomeViewModel.State) {
    visibility = when (state) {
        HomeViewModel.State.LOADING -> {
            setImageResource(R.drawable.loading_animation)
            View.VISIBLE
        }
        HomeViewModel.State.FAILED -> {
            setImageResource(R.drawable.ic_connection_error)
            View.VISIBLE
        }
        HomeViewModel.State.SUCCESS -> {
            View.GONE
        }
    }
}