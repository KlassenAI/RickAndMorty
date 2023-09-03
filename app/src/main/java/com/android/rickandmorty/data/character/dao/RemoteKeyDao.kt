package com.android.rickandmorty.data.character.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.rickandmorty.domain.character.entities.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRemoteKeys(remoteKey: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_key WHERE character_id = :characterId")
    fun getRemoteKeyByCharacterId(characterId: Int): RemoteKeyEntity?

    @Query("DELETE FROM remote_key WHERE current_page = :page")
    fun clearRemoteKeysByPage(page: Int)
}