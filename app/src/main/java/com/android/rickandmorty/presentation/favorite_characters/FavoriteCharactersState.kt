package com.android.rickandmorty.presentation.favorite_characters

import com.android.rickandmorty.domain.character.entities.ui.FavoriteCharacterListItem
import com.android.rickandmorty.presentation.core.state.BaseState

data class FavoriteCharactersState(
    val characters: List<FavoriteCharacterListItem> = listOf(),
): BaseState {
    val status = when {
        characters.isEmpty() -> FavoriteCharactersStateStatus.Empty
        else -> FavoriteCharactersStateStatus.Success(characters)
    }
}