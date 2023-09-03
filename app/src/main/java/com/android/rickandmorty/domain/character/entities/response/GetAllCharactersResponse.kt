package com.android.rickandmorty.domain.character.entities.response

data class GetAllCharactersResponse(
    val results: List<CharacterResponse>
)