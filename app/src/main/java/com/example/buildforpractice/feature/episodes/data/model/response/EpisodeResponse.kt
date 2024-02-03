package com.example.buildforpractice.feature.episodes.data.model.response

import com.google.gson.annotations.SerializedName

data class EpisodesResponse(val episodes: List<EpisodeResponse>)

data class EpisodeResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode") val episodeCode: String,
    @SerializedName("characters") val characterUrls: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
)