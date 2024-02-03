package com.example.buildforpractice.feature.episodes.data.source

import com.example.buildforpractice.utils.network.toResource
import javax.inject.Inject

class EpisodeRemoteDataSource @Inject constructor(
    private val episodeService: EpisodesService
) {

    suspend fun getEpisodes(episodesIds: List<String>) =
        episodeService.getEpisodesByEpisodesId(episodesIds).toResource()
}