package com.example.buildforpractice.feature.pagingCharacters.data.source.remote

import com.example.buildforpractice.feature.characters.data.model.response.UnifiedCharacterResponse
import com.example.buildforpractice.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetCharactersPagingService {

    @GET(Constants.RickAndMorty_CHARACTER_URL)
    suspend fun getCharactersByPage(@Query("page") page:Int): Response<UnifiedCharacterResponse>
}