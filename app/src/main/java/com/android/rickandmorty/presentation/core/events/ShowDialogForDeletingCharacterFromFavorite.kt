package com.android.rickandmorty.presentation.core.events

interface ShowDialogForDeletingCharacterFromFavorite {
    val title: String
    val message: String
    val positiveBtnTitle: String
    val negativeBtnTitle: String
    val onSuccess: () -> Unit
}