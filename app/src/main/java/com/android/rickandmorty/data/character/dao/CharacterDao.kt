package com.android.rickandmorty.data.character.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.android.rickandmorty.domain.character.entities.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCharacter(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * FROM character ORDER BY page")
    fun getCharacterPagingSource(): PagingSource<Int, CharacterEntity>

    @Transaction
    fun switchCharacterIsFavorite(characterId: Int) {
        val isFavorite = getCharacterIsFavoriteById(characterId)
        updateCharacterIsFavorite(characterId, !isFavorite)
    }

    @Query("SELECT is_favorite FROM character WHERE id = :characterId")
    fun getCharacterIsFavoriteById(characterId: Int): Boolean

    @Query("UPDATE character SET is_favorite = :isFavorite WHERE id = :characterId")
    fun updateCharacterIsFavorite(characterId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM character WHERE is_favorite = 1")
    fun getFavoriteCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM character WHERE id = :characterId")
    fun getCharacterById(characterId: Int): Flow<CharacterEntity>

    @Query("SELECT id FROM character WHERE is_favorite = 1 AND (page = :page OR id IN (:ids))")
    fun getFavoriteCharacterIdsByIdsAndPage(ids: List<Int>, page: Int): List<Int>

    @Query("DELETE FROM character WHERE page = :page")
    fun clearAllCharactersByPage(page: Int)


}