package com.android.rickandmorty.presentation.all_characters

import androidx.paging.PagingData
import com.android.rickandmorty.domain.character.entities.ui.CharacterListItem

data class AllCharactersState(
    val characters: PagingData<CharacterListItem> = PagingData.empty(),
)