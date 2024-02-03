package com.example.buildforpractice.feature.characters.data.repository

import GetCharactersPagingDataSource
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.buildforpractice.feature.characters.data.model.domain.RMCharacter
import com.example.buildforpractice.feature.characters.data.model.entity.CharacterEntity
import com.example.buildforpractice.feature.characters.data.model.toDomain
import com.example.buildforpractice.feature.characters.data.model.toEntity
import com.example.buildforpractice.feature.characters.data.source.local.CharacterLocalDataSource
import com.example.buildforpractice.feature.characters.data.source.remote.GetCharactersRemoteDataSource
import com.example.buildforpractice.feature.characters.presentation.ui.CharactersPagingAdapter
import com.example.buildforpractice.feature.pagingCharacters.data.source.remote.GetCharactersPagingService
import com.example.buildforpractice.utils.IoDispatcher
import com.example.buildforpractice.utils.network.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PagingCharactersRepositoryImpl @Inject constructor(
    private val service: GetCharactersPagingService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : PagingCharactersRepository {
    override fun getCharactersByPage(): Flow<PagingData<RMCharacter>> {
        return Pager(
            config = PagingConfig(10, 2),
            pagingSourceFactory = { GetCharactersPagingDataSource(service) }).flow.flowOn(
            ioDispatcher
        )
    }
}