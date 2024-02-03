package com.example.buildforpractice.feature.episodes.di

import com.example.buildforpractice.feature.characters.data.source.remote.GetCharactersService
import com.example.buildforpractice.feature.episodes.data.source.EpisodesService
import com.example.buildforpractice.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES).addInterceptor(httpLoggingInterceptor)
        }.build()


    @Provides
    fun provideEpisodesService(okHttpClient: OkHttpClient): EpisodesService {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.RickAndMorty_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(EpisodesService::class.java)
    }
}