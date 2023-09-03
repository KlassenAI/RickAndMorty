package com.android.rickandmorty.core.extensions

import android.content.Context
import android.widget.ImageView
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.rickandmorty.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(getPlaceholderLoader(context))
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .error(R.drawable.default_character_image)
        .into(this)
}

private fun getPlaceholderLoader(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            context.getColor(R.color.white),
            BlendModeCompat.SRC_ATOP
        )
        start()
    }
}