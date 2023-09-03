package com.android.rickandmorty.core.extensions

import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat

fun TextView.setStartColoredDrawable(
    @DrawableRes drawableId: Int,
    @DimenRes dimenRes: Int,
    @ColorInt color: Int
) {
    val drawable = ContextCompat.getDrawable(context, drawableId)
    val size = resources.getDimensionPixelSize(dimenRes)
    drawable?.setBounds(0, 0, size, size)
    drawable?.colorFilter =
        BlendModeColorFilterCompat.createBlendModeColorFilterCompat(color, BlendModeCompat.SRC_ATOP)
    this.setCompoundDrawables(drawable, null, null, null)
}