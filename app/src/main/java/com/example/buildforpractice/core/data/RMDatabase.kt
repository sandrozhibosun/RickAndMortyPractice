package com.example.buildforpractice.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.buildforpractice.feature.characters.data.model.entity.CharacterEntity
import com.example.buildforpractice.feature.characters.data.source.local.CharacterDao

@Database(entities = [CharacterEntity::class], version = 1, exportSchema = false)
abstract class RMDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}