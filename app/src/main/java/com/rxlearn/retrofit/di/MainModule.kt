package com.rxlearn.retrofit.di

import com.rxlearn.retrofit.data.news.JsonPlaceHolderApi
import com.rxlearn.retrofit.data.news.JsonPlaceHolderSingleton
import com.rxlearn.retrofit.data.news.Repository
import com.rxlearn.retrofit.data.product.JsonPlaceHolderProduct
import com.rxlearn.retrofit.data.product.ProductApi
import com.rxlearn.retrofit.data.product.RepositoryProduct
import com.rxlearn.retrofit.data.weather.JsonPlaceHolderWeather
import com.rxlearn.retrofit.data.weather.RepositoryWeather
import com.rxlearn.retrofit.data.weather.WeatherApi
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

    @Provides
    @Singleton
    fun provideRepositoryProduct(api: ProductApi): RepositoryProduct {
        return RepositoryProduct(api)
    }


    @Provides
    @Singleton
    fun provideJsonPlaceHolderProductApi(): ProductApi {
        return JsonPlaceHolderProduct.api
    }

    @Provides
    @Singleton
    fun provideRepositoryWeather(api: WeatherApi): RepositoryWeather {
        return RepositoryWeather(api)
    }


    @Provides
    @Singleton
    fun provideJsonPlaceHolderWeatherApi(): WeatherApi {
        return JsonPlaceHolderWeather.api
    }



}