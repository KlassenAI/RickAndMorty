package com.android.rickandmorty.domain.character.use_cases

import com.android.rickandmorty.core.request.RequestResult
import com.android.rickandmorty.data.character.repository.CharacterRepository
import com.android.rickandmorty.domain.character.entities.ui.CharacterItem
import kotlinx.coroutines.flow.Flow

class GetDetailCharacterItemByIdUseCase(
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(characterId: Int): Flow<RequestResult<CharacterItem>> {
        return characterRepository.getDetailCharacterItemById(characterId)
    }
}