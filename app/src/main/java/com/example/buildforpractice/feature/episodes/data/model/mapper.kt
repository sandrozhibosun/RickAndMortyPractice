package com.example.buildforpractice.feature.episodes.data.model

import com.example.buildforpractice.feature.episodes.data.model.domain.Episode
import com.example.buildforpractice.feature.episodes.data.model.response.EpisodeResponse

fun EpisodeResponse.toDomain(): Episode {
    return Episode(
        id = this.id,
        name = this.name,
        airDate = this.airDate,
        episodeCode = this.episodeCode,
    )
}