package com.example.buildforpractice.feature.characters.data.source.remote

import com.example.buildforpractice.utils.network.toResource
import javax.inject.Inject

class GetCharactersRemoteDataSource @Inject constructor(
    private val charactersService: GetCharactersService
) {
    suspend fun getCharacters() = charactersService.getCharacters().toResource()
}