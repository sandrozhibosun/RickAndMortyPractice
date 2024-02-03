package com.example.buildforpractice.core.di

import com.example.buildforpractice.feature.characters.data.repository.CharactersRepository
import com.example.buildforpractice.feature.characters.data.repository.CharactersRepositoryImpl
import com.example.buildforpractice.feature.characters.data.repository.PagingCharactersRepository
import com.example.buildforpractice.feature.characters.data.repository.PagingCharactersRepositoryImpl
import com.example.buildforpractice.feature.episodes.data.repository.EpisodesRepository
import com.example.buildforpractice.feature.episodes.data.repository.EpisodesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCharacterRepository(
        characterRepositoryImpl: CharactersRepositoryImpl
    ): CharactersRepository
    @Binds
    abstract fun bindEpisodesRepository(
        episodeRepositoryImpl: EpisodesRepositoryImpl
    ): EpisodesRepository

    @Binds
    abstract fun bindCharacterPagingRepository(
        pagingCharactersRepositoryImpl: PagingCharactersRepositoryImpl
    ): PagingCharactersRepository
}