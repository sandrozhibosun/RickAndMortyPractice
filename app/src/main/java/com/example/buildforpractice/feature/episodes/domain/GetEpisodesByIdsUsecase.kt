package com.example.buildforpractice.feature.episodes.domain

import com.example.buildforpractice.feature.episodes.data.repository.EpisodesRepository
import javax.inject.Inject

class GetEpisodesByIdsUsecase @Inject constructor(
    private val episodesRepository: EpisodesRepository) {
    operator fun invoke(episodesIds: List<String>) = episodesRepository.getEpisodes(episodesIds)
}
