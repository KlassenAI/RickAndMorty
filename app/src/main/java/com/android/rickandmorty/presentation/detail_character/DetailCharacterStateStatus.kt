package com.android.rickandmorty.presentation.detail_character

import com.android.rickandmorty.domain.character.entities.ui.CharacterItem

sealed class DetailCharacterStateStatus {
    data object Loading : DetailCharacterStateStatus()
    data class Error(val message: String) : DetailCharacterStateStatus()
    data class Success(val character: CharacterItem) : DetailCharacterStateStatus()
}