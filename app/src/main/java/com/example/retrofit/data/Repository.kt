package com.example.retrofit.data

class Repository(private val api: JsonPlaceHolderApi) {

    fun getAllUserData() = api.getAllUserData()

    fun getAllPhotoData() = api.getAllPhotoData()
}