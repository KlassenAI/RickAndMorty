package com.android.rickandmorty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.rickandmorty.data.character.dao.CharacterDao
import com.android.rickandmorty.data.character.dao.RemoteKeyDao
import com.android.rickandmorty.domain.character.entities.entity.CharacterEntity
import com.android.rickandmorty.domain.character.entities.entity.RemoteKeyEntity

@Database(
    entities = [CharacterEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CharacterDatabase : RoomDatabase() {

    companion object {
        const val CHARACTER_DATABASE_NAME = "character_db"
    }

    abstract fun getCharacterDao(): CharacterDao
    abstract fun getRemoteKeyDao(): RemoteKeyDao
}