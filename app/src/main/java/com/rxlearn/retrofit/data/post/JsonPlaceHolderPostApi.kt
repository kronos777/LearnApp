package com.rxlearn.retrofit.data.post

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonPlaceHolderPostApi {

    @GET("posts")
    suspend fun getAllPosts(): Posts


    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post
//'https://dummyjson.com/posts?sortBy=title&order=asc'

    @GET("posts")
    suspend fun getPostsByFilter(@Query("sortBy") title: String, @Query("order") order: String): Posts


}