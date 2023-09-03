package com.android.rickandmorty.presentation.detail_character

import com.android.rickandmorty.domain.character.entities.ui.CharacterItem

data class DetailCharacterState(
    val isLoading: Boolean = true,
    val errorMessage: String = "",
    val character: CharacterItem? = null,
) {

    companion object {
        private const val EXCEPTION_NO_STATUS_ACCEPTABLE_WITH_SUCH_STATE =
            "No status acceptable with such state"
    }

    val status: DetailCharacterStateStatus = when {
        isLoading -> DetailCharacterStateStatus.Loading
        errorMessage.isNotEmpty() -> DetailCharacterStateStatus.Error(errorMessage)
        character != null -> DetailCharacterStateStatus.Success(character)
        else -> throw Exception(EXCEPTION_NO_STATUS_ACCEPTABLE_WITH_SUCH_STATE)
    }
}