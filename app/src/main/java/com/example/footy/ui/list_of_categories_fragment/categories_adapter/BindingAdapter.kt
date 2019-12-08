package com.example.footy.ui.list_of_categories_fragment.categories_adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.footy.R
import com.example.footy.network.Meal
import com.example.footy.ui.list_of_categories_fragment.HomeViewModel

@BindingAdapter("setImage")
fun setImage(imageView: ImageView, imgString: String?) {
    imgString?.let {
        val imageUri = imgString.toUri()
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


@BindingAdapter("setRoundImage")
fun setRoundImage(imageView: ImageView, item: Meal?) {
    item?.let {
        val imageUri = it.strMealThumb?.toUri()
        Glide.with(imageView.context)
            .load(imageUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .circleCrop()
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


@BindingAdapter("setVisibility")
fun TextView.setVisibility(favouriteListSize: Int): Unit {
    visibility = if (favouriteListSize == 0) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
