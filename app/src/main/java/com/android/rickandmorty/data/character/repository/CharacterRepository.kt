package com.android.rickandmorty.data.character.repository

import androidx.paging.PagingData
import com.android.rickandmorty.core.request.RequestResult
import com.android.rickandmorty.domain.character.entities.ui.CharacterItem
import com.android.rickandmorty.domain.character.entities.ui.CharacterListItem
import com.android.rickandmorty.domain.character.entities.ui.FavoriteCharacterListItem
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun switchCharacterIsFavorite(characterId: Int)

    fun getPagingCharacterListItems(): Flow<PagingData<CharacterListItem>>

    fun getDetailCharacterItemById(characterId: Int): Flow<RequestResult<CharacterItem>>

    fun getFavoriteCharacterListItems(): Flow<List<FavoriteCharacterListItem>>
}
