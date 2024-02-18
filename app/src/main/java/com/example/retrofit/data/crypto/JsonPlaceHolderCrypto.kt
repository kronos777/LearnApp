package com.example.retrofit.data.crypto

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JsonPlaceHolderCrypto {

    private const val BASE_URL = "https://min-api.cryptocompare.com/data/"

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


    val api: CryptoApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(returnOkHttpClient())
        .build()
        .create(CryptoApi::class.java)

}