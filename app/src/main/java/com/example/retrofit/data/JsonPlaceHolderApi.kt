package com.example.retrofit.data

import io.reactivex.Single
import retrofit2.http.GET

interface JsonPlaceHolderApi{

    @GET("users")
    fun getAllUserData(): Single<List<UserData>>

    @GET("photos")
    fun getAllPhotoData(): Single<List<PhotoData>>

}