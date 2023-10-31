package com.example.retrofit.data.product

import com.example.retrofit.data.user.AuthRequest
import com.example.retrofit.data.user.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {

   @GET("products/{id}")
   suspend fun getProductById(@Path("id") id: Int): Product

   @POST("auth/login")
   suspend fun auth(@Body authRequest: AuthRequest): User

}