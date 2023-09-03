package com.android.rickandmorty.presentation.favorite_characters

import com.android.rickandmorty.domain.character.entities.ui.FavoriteCharacterListItem

sealed class FavoriteCharactersStateStatus {
    data object Empty : FavoriteCharactersStateStatus()
    data class Success(val characters: List<FavoriteCharacterListItem>) : FavoriteCharactersStateStatus()
}