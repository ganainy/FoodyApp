package com.example.footy.ui.home_fragment.categories_adapter

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.footy.R
import com.example.footy.network.Category

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