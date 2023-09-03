package com.android.rickandmorty.data.character.api

import com.android.rickandmorty.domain.character.entities.response.CharacterResponse
import com.android.rickandmorty.domain.character.entities.response.GetAllCharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("character")
    suspend fun getCharactersByPageAndCount(
        @Query("page") page: Int,
        @Query("count") count: Int
    ): GetAllCharactersResponse

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") characterId: Int
    ): CharacterResponse
}