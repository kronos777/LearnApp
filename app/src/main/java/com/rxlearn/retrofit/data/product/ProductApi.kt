package com.rxlearn.retrofit.data.product

import com.rxlearn.retrofit.data.user.AuthRequest
import com.rxlearn.retrofit.data.user.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

   @GET("products/{id}")
   suspend fun getProductById(@Path("id") id: Int): Product

   @POST("auth/login")
   suspend fun auth(@Body authRequest: AuthRequest): User


   @GET("products")
   suspend fun getAllProducts(): Products


   @Headers(
      "Content-Type: application/json"
   )
   @GET("auth/products/search")
   suspend fun getProductsByName(@Header("Authorization") token: String, @Query("q") name: String): Products


}