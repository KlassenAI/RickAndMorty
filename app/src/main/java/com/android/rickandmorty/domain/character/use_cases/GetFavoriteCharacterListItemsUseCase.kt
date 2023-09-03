package com.android.rickandmorty.domain.character.use_cases

import com.android.rickandmorty.data.character.repository.CharacterRepository
import com.android.rickandmorty.domain.character.entities.ui.FavoriteCharacterListItem
import kotlinx.coroutines.flow.Flow

class GetFavoriteCharacterListItemsUseCase(
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(): Flow<List<FavoriteCharacterListItem>> {
        return characterRepository.getFavoriteCharacterListItems()
    }
}