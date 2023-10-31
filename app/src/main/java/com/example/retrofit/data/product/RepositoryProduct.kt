package com.example.retrofit.data.product

import retrofit2.http.Path

class RepositoryProduct(val api: ProductApi) {

    suspend fun getProductById(id: Int) = api.getProductById(id)

}