package com.android.rickandmorty.domain.character.use_cases

import com.android.rickandmorty.data.character.repository.CharacterRepository

class SwitchCharacterIsFavoriteUseCase(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(characterId: Int) {
        characterRepository.switchCharacterIsFavorite(characterId)
    }
}