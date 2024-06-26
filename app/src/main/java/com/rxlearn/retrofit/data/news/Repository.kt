package com.rxlearn.retrofit.data.news

class Repository(private val api: JsonPlaceHolderApi) {

    fun getAllUserData() = api.getAllUserData()

    fun getAllPhotoData() = api.getAllPhotoData()
}