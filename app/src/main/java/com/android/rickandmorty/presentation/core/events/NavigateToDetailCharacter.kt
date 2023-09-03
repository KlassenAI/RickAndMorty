package com.android.rickandmorty.presentation.core.events

import androidx.annotation.IdRes

interface NavigateToDetailCharacter {
    @get:IdRes
    val actionId: Int
    val characterIdWithKey: Pair<String, Int>
}