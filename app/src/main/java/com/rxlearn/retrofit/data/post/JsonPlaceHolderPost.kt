package com.rxlearn.retrofit.data.post

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object JsonPlaceHolderPost {

    val api: JsonPlaceHolderPostApi = Retrofit.Builder()
        .baseUrl("https://dummyjson.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JsonPlaceHolderPostApi::class.java)

}