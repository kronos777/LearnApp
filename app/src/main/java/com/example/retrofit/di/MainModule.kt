package com.example.retrofit.di

import com.example.retrofit.data.JsonPlaceHolderApi
import com.example.retrofit.data.JsonPlaceHolderSingleton
import com.example.retrofit.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideRepository(api: JsonPlaceHolderApi): Repository {
        return Repository(api)
    }

    @Provides
    @Singleton
    fun provideJsonPlaceHolderApi(): JsonPlaceHolderApi {
        return JsonPlaceHolderSingleton.api
    }
}