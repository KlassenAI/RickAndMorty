package com.android.rickandmorty.domain.character.entities.ui

data class CharacterItem(
    val name: String,
    val status: String,
    val statusColor: Int,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val image: String,
    val isFavorite: Boolean,
)