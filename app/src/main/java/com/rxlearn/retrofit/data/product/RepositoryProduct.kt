package com.rxlearn.retrofit.data.product

import com.rxlearn.retrofit.data.user.AuthRequest

class RepositoryProduct(val api: ProductApi) {

    suspend fun getProductById(id: Int) = api.getProductById(id)
    suspend fun auth(authRequest: AuthRequest) = api.auth(authRequest)
    suspend fun getAllProducts() = api.getAllProducts()
    suspend fun getProductsByName(token: String, name: String) = api.getProductsByName(token, name)


}