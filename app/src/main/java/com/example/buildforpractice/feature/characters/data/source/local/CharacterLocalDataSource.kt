package com.example.buildforpractice.feature.characters.data.source.local

import com.example.buildforpractice.core.data.RMDatabase
import com.example.buildforpractice.feature.characters.data.model.entity.CharacterEntity
import javax.inject.Inject

class CharacterLocalDataSource @Inject constructor(
    private val database: RMDatabase
) {

    suspend fun saveCharactersLocally(characterEntities: List<CharacterEntity>) {
        database.characterDao().insert(characterEntities)
    }

    fun getCharactersLocally() = database.characterDao().getCharacters()
}