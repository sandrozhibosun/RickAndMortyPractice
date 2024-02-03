package com.example.buildforpractice.feature.episodes.data.repository

import com.example.buildforpractice.feature.episodes.data.model.domain.Episode
import com.example.buildforpractice.utils.network.Resource
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {

    fun getEpisodes(episodesIds: List<String>): Flow<Resource<List<Episode>>>
}