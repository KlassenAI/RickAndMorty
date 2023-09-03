package com.android.rickandmorty.presentation.all_characters

import androidx.annotation.IdRes
import com.android.rickandmorty.presentation.core.events.NavigateToDetailCharacter
import com.android.rickandmorty.presentation.core.events.ShowDialogForDeletingCharacterFromFavorite

sealed class AllCharactersEvents {
    data class ShowDialogForDeletingCharacterFromFavoriteImpl(
        override val title: String,
        override val message: String,
        override val positiveBtnTitle: String,
        override val negativeBtnTitle: String,
        override val onSuccess: () -> Unit,
    ): AllCharactersEvents(), ShowDialogForDeletingCharacterFromFavorite
    data class NavigateToDetailCharacterImpl(
        @IdRes override val actionId: Int,
        override val characterIdWithKey: Pair<String, Int>
    ) : AllCharactersEvents(), NavigateToDetailCharacter
}