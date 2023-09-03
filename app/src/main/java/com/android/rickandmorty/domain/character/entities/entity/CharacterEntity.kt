package com.android.rickandmorty.domain.character.entities.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("character")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String,
    val page: Int,
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean,
)