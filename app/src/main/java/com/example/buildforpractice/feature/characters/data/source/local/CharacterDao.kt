package com.example.buildforpractice.feature.characters.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.buildforpractice.feature.characters.data.model.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table")
    fun getCharacters(): Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(article: List<CharacterEntity>)

    @Query("DELETE FROM character_table")
    suspend fun deleteAll()

    @Transaction
    suspend fun cleanAndCacheArticles(articles: List<CharacterEntity>) {
        deleteAll()
        insert(articles)
    }
}