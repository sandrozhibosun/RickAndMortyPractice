package com.example.buildforpractice.feature.characters.data.source.remote

import com.example.buildforpractice.feature.characters.data.model.response.UnifiedCharacterResponse
import com.example.buildforpractice.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface GetCharactersService {

    @GET(Constants.RickAndMorty_CHARACTER_URL)
    suspend fun getCharacters(): Response<UnifiedCharacterResponse>
}