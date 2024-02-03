package com.example.buildforpractice.feature.characters.di

import com.example.buildforpractice.feature.characters.data.source.remote.GetCharactersService
import com.example.buildforpractice.feature.pagingCharacters.data.source.remote.GetCharactersPagingService
import com.example.buildforpractice.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class PagingCharacterNetworkModule {


    @Provides
    fun provideGetPagingCharacterService(okHttpClient: OkHttpClient): GetCharactersPagingService {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.RickAndMorty_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(GetCharactersPagingService::class.java)
    }
}