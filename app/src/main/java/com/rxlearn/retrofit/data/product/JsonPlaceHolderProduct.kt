package com.rxlearn.retrofit.data.product

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JsonPlaceHolderProduct {

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

    val api: ProductApi = Retrofit.Builder()
        .baseUrl("https://dummyjson.com")
        //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(returnOkHttpClient())
        .build()
        .create(ProductApi::class.java)
}