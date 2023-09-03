package com.android.rickandmorty.domain.character.entities.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("remote_key")
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "character_id")
    val characterId: Int,
    @ColumnInfo(name = "prev_key")
    val prevKey: Int?,
    @ColumnInfo(name = "current_page")
    val currentPage: Int,
    @ColumnInfo(name = "next_key")
    val nextKey: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
