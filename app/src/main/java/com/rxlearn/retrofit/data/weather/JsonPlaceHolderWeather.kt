package com.rxlearn.retrofit.data.weather

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JsonPlaceHolderWeather {

    fun returnOkHttpClient(): OkHttpClient {
        /*okhttp*/
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        /*okhttp*/
        return client
    }

    val api: WeatherApi = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/v1/")
        //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(returnOkHttpClient())
        .build()
        .create(WeatherApi::class.java)

}