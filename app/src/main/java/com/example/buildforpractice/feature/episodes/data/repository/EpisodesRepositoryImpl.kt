package com.example.buildforpractice.feature.episodes.data.repository

import android.util.Log
import com.example.buildforpractice.feature.episodes.data.model.domain.Episode
import com.example.buildforpractice.feature.episodes.data.model.toDomain
import com.example.buildforpractice.feature.episodes.data.source.EpisodeRemoteDataSource
import com.example.buildforpractice.utils.IoDispatcher
import com.example.buildforpractice.utils.network.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val episodeRemoteDataSource: EpisodeRemoteDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : EpisodesRepository {
    override fun getEpisodes(episodesIds: List<String>): Flow<Resource<List<Episode>>> {
        return flow {
            when (val resource = episodeRemoteDataSource.getEpisodes(episodesIds)) {
                is Resource.Success -> {
                    emit(
                        Resource.Success(resource.value.map { it.toDomain() })
                    )
                }

                is Resource.Failure -> {
                    emit(Resource.Failure(false, null, null))
                }

                else -> {}
            }
        }.catch {
            emit(Resource.Failure(false, null, it.message))
        }
            .flowOn(ioDispatcher)
    }
}