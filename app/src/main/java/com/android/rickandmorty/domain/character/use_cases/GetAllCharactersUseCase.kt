package com.android.rickandmorty.domain.character.use_cases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import com.android.rickandmorty.data.character.repository.CharacterRepository
import com.android.rickandmorty.domain.character.entities.ui.CharacterListItem
import kotlinx.coroutines.flow.Flow

class GetAllCharactersUseCase(
    private val characterRepository: CharacterRepository,
) {

    operator fun invoke(): Flow<PagingData<CharacterListItem>> {
        return characterRepository.getPagingCharacterListItems()
    }
}