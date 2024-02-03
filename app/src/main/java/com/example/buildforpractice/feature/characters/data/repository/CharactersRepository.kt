package com.example.buildforpractice.feature.characters.data.repository

import com.example.buildforpractice.feature.characters.data.model.domain.RMCharacter
import com.example.buildforpractice.utils.network.Resource
import kotlinx.coroutines.flow.Flow


interface CharactersRepository {
    fun getCharactersNetworkFirst(): Flow<Resource<List<RMCharacter>>>
    fun getCharactersCacheFirst(): Flow<Resource<List<RMCharacter>>>
    fun refreshCharacters(): Flow<Resource<Unit>>
}