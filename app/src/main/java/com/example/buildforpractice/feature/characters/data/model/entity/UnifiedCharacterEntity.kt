package com.example.buildforpractice.feature.characters.data.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.buildforpractice.utils.Constants

@Entity(tableName = Constants.RickAndMorty_CHARACTER_TABLE)
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    @Embedded(prefix = "origin_") val origin: OriginEntity,
    @Embedded(prefix = "location_") val location: LocationEntity,
    val image: String,
    // Storing lists in a database column can be tricky; you might store episode IDs as a JSON string or handle them in a separate table
    val episode: String,
    val url: String,
    val created: String
)

// Depending on your database design, you might not need separate tables/entities for Origin and Location
data class OriginEntity(
    val name: String,
    val url: String
)

data class LocationEntity(
    val name: String,
    val url: String
)