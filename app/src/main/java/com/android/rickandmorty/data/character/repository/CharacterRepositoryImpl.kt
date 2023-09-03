package com.android.rickandmorty.data.character.repository

import android.graphics.Color
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.android.rickandmorty.core.request.RequestResult
import com.android.rickandmorty.data.character.dao.CharacterDao
import com.android.rickandmorty.data.character.mediator.CharacterRemoteMediator
import com.android.rickandmorty.data.character.mapper.toCharacterItem
import com.android.rickandmorty.data.character.mapper.toCharacterListItem
import com.android.rickandmorty.data.character.mapper.toFavoriteCharacterListItem
import com.android.rickandmorty.domain.character.entities.response.CharacterStatusColors
import com.android.rickandmorty.domain.character.entities.ui.CharacterItem
import com.android.rickandmorty.domain.character.entities.ui.CharacterListItem
import com.android.rickandmorty.domain.character.entities.ui.FavoriteCharacterListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharacterRepositoryImpl(
    private val characterDao: CharacterDao,
    private val characterRemoteMediator: CharacterRemoteMediator,
) : CharacterRepository {

    companion object {
        private const val EXCEPTION_GET_CHARACTER_BY_ID = "No character found in local database with id = "
    }

    override suspend fun switchCharacterIsFavorite(characterId: Int) {
        characterDao.switchCharacterIsFavorite(characterId)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingCharacterListItems(): Flow<PagingData<CharacterListItem>> {
        return Pager(
            config = PagingConfig(CharacterRemoteMediator.DEFAULT_PAGE_SIZE),
            remoteMediator = characterRemoteMediator,
            pagingSourceFactory = { characterDao.getCharacterPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { characterEntity ->
                val statusColor = getColorFromStatus(characterEntity.status)
                characterEntity.toCharacterListItem(statusColor)
            }
        }
    }

    override fun getDetailCharacterItemById(characterId: Int): Flow<RequestResult<CharacterItem>> {
        return characterDao.getCharacterById(characterId).map { characterEntity ->
            try {
                val statusColor = getColorFromStatus(characterEntity.status)
                RequestResult.Success(characterEntity.toCharacterItem(statusColor))
            } catch (e: Exception) {
                RequestResult.Error(EXCEPTION_GET_CHARACTER_BY_ID + characterId)
            }
        }
    }

    override fun getFavoriteCharacterListItems(): Flow<List<FavoriteCharacterListItem>> {
        return characterDao.getFavoriteCharacters().map { characterEntities ->
            characterEntities.map { characterEntity ->
                val statusColor = getColorFromStatus(characterEntity.status)
                characterEntity.toFavoriteCharacterListItem(statusColor)
            }
        }
    }

    private fun getColorFromStatus(status: String): Int {
        val colorString = when (status) {
            "Alive" -> CharacterStatusColors.STATUS_ALIVE
            "Dead" -> CharacterStatusColors.STATUS_DEAD
            else -> CharacterStatusColors.STATUS_UNKNOWN
        }
        return Color.parseColor(colorString)
    }
}