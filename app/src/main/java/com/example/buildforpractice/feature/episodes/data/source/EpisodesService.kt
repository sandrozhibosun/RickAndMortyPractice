package com.example.buildforpractice.feature.episodes.data.source

import com.example.buildforpractice.feature.characters.data.model.response.UnifiedCharacterResponse
import com.example.buildforpractice.feature.episodes.data.model.response.EpisodeResponse
import com.example.buildforpractice.feature.episodes.data.model.response.EpisodesResponse
import com.example.buildforpractice.utils.Constants
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodesService {
    @GET(Constants.RickAndMorty_EPISODE_URL_BY_IDS)
    suspend fun getEpisodesByEpisodesId(@Path("ids") ids: List<String>): Response<List<EpisodeResponse>>
}