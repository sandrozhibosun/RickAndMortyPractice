package com.example.buildforpractice.feature.characters.data.repository

import com.example.buildforpractice.feature.characters.data.model.domain.RMCharacter
import com.example.buildforpractice.feature.characters.data.model.entity.CharacterEntity
import com.example.buildforpractice.feature.characters.data.model.response.CharacterResponse
import com.example.buildforpractice.feature.characters.data.model.toDomain
import com.example.buildforpractice.feature.characters.data.model.toEntity
import com.example.buildforpractice.feature.characters.data.source.local.CharacterLocalDataSource
import com.example.buildforpractice.feature.characters.data.source.remote.GetCharactersRemoteDataSource
import com.example.buildforpractice.utils.IoDispatcher
import com.example.buildforpractice.utils.network.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: GetCharactersRemoteDataSource,
    private val localDataSource: CharacterLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : CharactersRepository {

    //network first and cache fallback pattern
    override fun getCharactersNetworkFirst(): Flow<Resource<List<RMCharacter>>> {
        return flow {
            when (val resource = remoteDataSource.getCharacters()) {
                is Resource.Success -> {
                    emit(Resource.Success(resource.value.results.map { it.toDomain() }))
                    saveCharactersLocally(resource.value.results.map { it.toEntity() })
                }

                is Resource.Failure -> {
                    emit(resource)
                    emitAll(getCharactersFromLocal())
                }

                else -> Resource.Failure(false, null, "Unknown error")
            }
        }.flowOn(ioDispatcher)
    }

    // cache first - network fall back will be like this
    override fun getCharactersCacheFirst(): Flow<Resource<List<RMCharacter>>> {
        return localDataSource.getCharactersLocally().map { cache ->
            if (cache.isNotEmpty()) {
                Resource.Success(cache.map { it.toDomain() })
            } else {
                getCharactersFromRemote()
            }
        }.flowOn(ioDispatcher)
    }

    private suspend fun getCharactersFromRemote(): Resource<List<RMCharacter>> {
        return when (val resource = remoteDataSource.getCharacters()) {
            is Resource.Success -> {
                saveCharactersLocally(resource.value.results.map { it.toEntity() })
                // if we don't need to return data directly from network, can remove this line.
                // and then return a failure as "cache error, fetch from network" instead

//                Resource.Success(resource.value.results.map { it.toDomain() })
                Resource.Failure(false, null, "cache error, fetch from network")
            }

            is Resource.Failure -> {
                resource
            }

            else -> Resource.Failure(false, null, "Unknown error")
        }
    }

    override fun refreshCharacters(): Flow<Resource<Unit>> {
        return flow {
            when (val resource = remoteDataSource.getCharacters()) {
                is Resource.Success -> {
                    saveCharactersLocally(resource.value.results.map { it.toEntity() })
                }

                is Resource.Failure -> {
                    emit(resource)
                }

                else -> emit(Resource.Failure(false, null, "Unknown error"))
            }
        }.flowOn(ioDispatcher)
    }
    //


    private fun getCharactersFromLocal(): Flow<Resource<List<RMCharacter>>> {
        return localDataSource.getCharactersLocally().map { characterEntities ->
            if (characterEntities.isNotEmpty()) {
                Resource.Success(characterEntities.map { it.toDomain() })
            } else {
                Resource.Failure(false, null, "No data found")
            }
        }
    }

    private suspend fun saveCharactersLocally(characterEntities: List<CharacterEntity>) {
        withContext(ioDispatcher) {
            localDataSource.saveCharactersLocally(characterEntities)
        }
    }
}