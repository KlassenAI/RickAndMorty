package com.android.rickandmorty.data.character.mapper

import com.android.rickandmorty.domain.character.entities.entity.CharacterEntity
import com.android.rickandmorty.domain.character.entities.response.CharacterResponse
import com.android.rickandmorty.domain.character.entities.ui.CharacterItem
import com.android.rickandmorty.domain.character.entities.ui.CharacterListItem
import com.android.rickandmorty.domain.character.entities.ui.FavoriteCharacterListItem

fun CharacterResponse.toCharacterEntity(page: Int, isFavorite: Boolean) = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type.ifEmpty { "No Subtypes" },
    gender = gender,
    origin = origin.name,
    location = location.name,
    image = image,
    page = page,
    isFavorite = isFavorite
)

fun CharacterEntity.toCharacterListItem(statusColor: Int) = CharacterListItem(
    id = id,
    name = name,
    status = status,
    statusColor = statusColor,
    location = location,
    image = image,
    isFavorite = isFavorite,
)

fun CharacterEntity.toCharacterItem(statusColor: Int) = CharacterItem(
    name = name,
    status = status,
    statusColor = statusColor,
    species = species,
    type = type,
    gender = gender,
    origin = origin,
    location = location,
    image = image,
    isFavorite = isFavorite,
)

fun CharacterEntity.toFavoriteCharacterListItem(statusColor: Int) = FavoriteCharacterListItem(
    id = id,
    name = name,
    status = status,
    statusColor = statusColor,
    location = location,
    image = image,
)