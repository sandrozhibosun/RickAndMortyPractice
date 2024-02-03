package com.example.buildforpractice.feature.characters.data.repository

import androidx.paging.PagingData
import com.example.buildforpractice.feature.characters.data.model.domain.RMCharacter
import com.example.buildforpractice.utils.network.Resource
import kotlinx.coroutines.flow.Flow


interface PagingCharactersRepository {
    fun getCharactersByPage(): Flow<PagingData<RMCharacter>>
}