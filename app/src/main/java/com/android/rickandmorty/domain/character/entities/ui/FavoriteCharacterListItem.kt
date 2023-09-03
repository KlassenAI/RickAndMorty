package com.android.rickandmorty.domain.character.entities.ui

import com.android.rickandmorty.core.recycler.ListItem

data class FavoriteCharacterListItem(
    val id: Int,
    val name: String,
    val status: String,
    val statusColor: Int,
    val location: String,
    val image: String,
): ListItem {
    override fun getId(): Any = id
}