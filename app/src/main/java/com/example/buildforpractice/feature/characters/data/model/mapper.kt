package com.example.buildforpractice.feature.characters.data.model

import com.example.buildforpractice.feature.characters.data.model.domain.Location
import com.example.buildforpractice.feature.characters.data.model.domain.Origin
import com.example.buildforpractice.feature.characters.data.model.domain.RMCharacter
import com.example.buildforpractice.feature.characters.data.model.entity.CharacterEntity
import com.example.buildforpractice.feature.characters.data.model.entity.LocationEntity
import com.example.buildforpractice.feature.characters.data.model.entity.OriginEntity
import com.example.buildforpractice.feature.characters.data.model.response.CharacterResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun CharacterResponse.toEntity(): CharacterEntity = CharacterEntity(
    id = this.id,
    name = this.name,
    status = this.status,
    species = this.species,
    type = this.type,
    gender = this.gender,
    origin = OriginEntity(name = this.origin.name, url = this.origin.url),
    location = LocationEntity(name = this.location.name, url = this.location.url),
    image = this.image,
    episode = Gson().toJson(this.episode), // Convert list to JSON string
    url = this.url,
    created = this.created
)

fun CharacterEntity.toDomain(): RMCharacter = RMCharacter(
    id = this.id,
    name = this.name,
    status = this.status,
    species = this.species,
    type = this.type,
    gender = this.gender,
    origin = Origin(name = this.origin.name, url = this.origin.url),
    location = Location(name = this.location.name, url = this.location.url),
    image = this.image,
    episodes = Gson().fromJson(this.episode, object : TypeToken<List<String>>() {}.type), // Convert JSON string to list
    url = this.url,
    created = this.created
)

fun CharacterResponse.toDomain(): RMCharacter = RMCharacter(
    id = this.id,
    name = this.name,
    status = this.status,
    species = this.species,
    type = this.type,
    gender = this.gender,
    origin = Origin(name = this.origin.name, url = this.origin.url),
    location = Location(name = this.location.name, url = this.location.url),
    image = this.image,
    episodes = this.episode, // Convert list to JSON string
    url = this.url,
    created = this.created
)