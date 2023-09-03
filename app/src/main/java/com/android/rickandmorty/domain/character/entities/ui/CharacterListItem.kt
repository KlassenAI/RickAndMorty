package com.android.rickandmorty.domain.character.entities.ui

import com.android.rickandmorty.core.recycler.ListItem

data class CharacterListItem(
    val id: Int,
    val name: String,
    val status: String,
    val statusColor: Int,
    val location: String,
    val image: String,
    var isFavorite: Boolean,
): ListItem {
    override fun getId(): Any = id
}