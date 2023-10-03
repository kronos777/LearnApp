package com.example.retrofit.ui

import androidx.lifecycle.ViewModel
import com.example.retrofit.data.JsonPlaceHolderSingleton
import com.example.retrofit.data.Repository

class MainViewModel() : ViewModel() {
    private val repository: Repository = Repository(JsonPlaceHolderSingleton.api)

    fun getAllUserData() = repository.getAllUserData()

    fun getAllPhotoData() = repository.getAllPhotoData()
}