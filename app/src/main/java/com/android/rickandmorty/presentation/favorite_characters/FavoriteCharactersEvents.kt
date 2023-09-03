package com.android.rickandmorty.presentation.favorite_characters

import androidx.annotation.IdRes
import com.android.rickandmorty.presentation.core.events.NavigateToDetailCharacter
import com.android.rickandmorty.presentation.core.events.ShowDialogForDeletingCharacterFromFavorite

sealed class FavoriteCharactersEvents {

    data class ShowDialogForDeletingCharacterFromFavoriteImpl(
        override val title: String,
        override val message: String,
        override val positiveBtnTitle: String,
        override val negativeBtnTitle: String,
        override val onSuccess: () -> Unit,
    ) : FavoriteCharactersEvents(), ShowDialogForDeletingCharacterFromFavorite

    data class NavigateToDetailCharacterImpl(
        @IdRes override val actionId: Int,
        override val characterIdWithKey: Pair<String, Int>
    ) : FavoriteCharactersEvents(), NavigateToDetailCharacter
}