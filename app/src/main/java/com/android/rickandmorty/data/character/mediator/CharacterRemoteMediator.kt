package com.android.rickandmorty.data.character.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.rickandmorty.data.database.CharacterDatabase
import com.android.rickandmorty.data.character.api.CharacterApi
import com.android.rickandmorty.data.character.mapper.toCharacterEntity
import com.android.rickandmorty.domain.character.entities.entity.CharacterEntity
import com.android.rickandmorty.domain.character.entities.entity.RemoteKeyEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val characterDatabase: CharacterDatabase,
    private val characterRemoteSource: CharacterApi,
) : RemoteMediator<Int, CharacterEntity>() {

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }

    // todo слишком большая функция, стоит разделить
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {

        val currentPage = when (loadType) {
            REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        return try {

            val characterResult =
                characterRemoteSource.getCharactersByPageAndCount(currentPage, state.config.pageSize)
            val characters = characterResult.results
            val characterIds = characters.map { it.id }

            characterDatabase.withTransaction {

                // удаление ключей по страницам и создание новых (или замена)
                characterDatabase.getRemoteKeyDao().clearRemoteKeysByPage(currentPage)
                val remoteKeys = characterIds.map { id ->
                    RemoteKeyEntity(
                        characterId = id,
                        prevKey = if (currentPage > 1) currentPage - 1 else null,
                        currentPage = currentPage,
                        nextKey = if (characters.isEmpty()) null else currentPage + 1
                    )
                }
                characterDatabase.getRemoteKeyDao().insertRemoteKeys(remoteKeys)

                // получение идентификаторов избранных персонажей,
                // очистка персонажей по странице
                // создание новых персонажей (или замена старых) с учетом избранности
                val favoriteCharacterIds = characterDatabase.getCharacterDao().getFavoriteCharacterIdsByIdsAndPage(characterIds, currentPage)
                characterDatabase.getCharacterDao().clearAllCharactersByPage(currentPage)

                val characterEntities = characters.map { character ->
                    val isFavorite = favoriteCharacterIds.contains(character.id)
                    character.toCharacterEntity(currentPage, isFavorite)
                }
                characterDatabase.getCharacterDao().insertCharacters(characterEntities)
            }

            MediatorResult.Success(endOfPaginationReached = characters.isEmpty())

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharacterEntity>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                characterDatabase.getRemoteKeyDao().getRemoteKeyByCharacterId(it)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, CharacterEntity>
    ): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            characterDatabase.getRemoteKeyDao().getRemoteKeyByCharacterId(it.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, CharacterEntity>
    ): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            characterDatabase.getRemoteKeyDao().getRemoteKeyByCharacterId(it.id)
        }
    }
}