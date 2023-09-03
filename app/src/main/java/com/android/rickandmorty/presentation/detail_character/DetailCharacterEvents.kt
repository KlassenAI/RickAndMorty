package com.android.rickandmorty.presentation.detail_character

import com.android.rickandmorty.presentation.core.events.ShowDialogForDeletingCharacterFromFavorite

sealed class DetailCharacterEvents {
    data class ShowDialogForDeletingCharacterFromFavoriteImpl(
        override val title: String,
        override val message: String,
        override val positiveBtnTitle: String,
        override val negativeBtnTitle: String,
        override val onSuccess: () -> Unit,
    ): DetailCharacterEvents(), ShowDialogForDeletingCharacterFromFavorite
}