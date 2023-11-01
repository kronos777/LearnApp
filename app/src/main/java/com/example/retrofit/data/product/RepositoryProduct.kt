package com.example.retrofit.data.product

import com.example.retrofit.data.user.AuthRequest
import retrofit2.http.Path
import retrofit2.http.Query

class RepositoryProduct(val api: ProductApi) {

    suspend fun getProductById(id: Int) = api.getProductById(id)
    suspend fun auth(authRequest: AuthRequest) = api.auth(authRequest)
    suspend fun getAllProducts() = api.getAllProducts()
    suspend fun getProductsByName(name: String) = api.getProductsByName(name)


}