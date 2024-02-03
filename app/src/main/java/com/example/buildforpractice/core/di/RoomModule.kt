package com.example.buildforpractice.core.di

import android.content.Context
import androidx.room.Room
import com.example.buildforpractice.core.data.RMDatabase
import com.example.buildforpractice.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        RMDatabase::class.java,
        Constants.RickAndMorty_DATABASE_NAME
    )
        // .addMigrations(migration1To2)
        .build()
}