package com.android.rickandmorty.core.extensions

import com.android.rickandmorty.R
import com.android.rickandmorty.databinding.ItemCharacterBinding

fun ItemCharacterBinding.bind(
    image: String,
    name: String,
    status: String,
    location: String,
    statusColor: Int,
    isFavorite: Boolean,
) {
    ivImage.loadImage(image)
    tvName.text = name
    tvStatus.text = status
    tvLocation.text = location
    ivFavorite.isSelected = isFavorite
    tvStatus.setStartColoredDrawable(
        drawableId = R.drawable.round,
        dimenRes = R.dimen.default_padding,
        color = statusColor
    )
}