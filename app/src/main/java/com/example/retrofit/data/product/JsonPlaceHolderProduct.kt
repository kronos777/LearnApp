package com.example.retrofit.data.product

import com.example.retrofit.data.news.JsonPlaceHolderApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object JsonPlaceHolderProduct {

    val api: ProductApi = Retrofit.Builder()
        .baseUrl("https://dummyjson.com")
        //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductApi::class.java)
}