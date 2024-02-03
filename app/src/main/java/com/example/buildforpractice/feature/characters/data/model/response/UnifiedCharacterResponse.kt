package com.example.buildforpractice.feature.characters.data.model.response

import com.google.gson.annotations.SerializedName

data class UnifiedCharacterResponse(
    @SerializedName("info") val info: InfoResponse,
    @SerializedName("results") val results: List<CharacterResponse>
)

data class InfoResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: Any? // Use Any? because the type can vary, null in this case
)

data class CharacterResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("origin") val origin: OriginResponse,
    @SerializedName("location") val location: LocationResponse,
    @SerializedName("image") val image: String,
    @SerializedName("episode") val episode: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
)

data class OriginResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

data class LocationResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)