package com.rxlearn.retrofit.ui

import androidx.lifecycle.ViewModel
import com.rxlearn.retrofit.data.news.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    //private val repository: Repository = Repository(JsonPlaceHolderSingleton.api)

    fun getAllUserData() = repository.getAllUserData()

    fun getAllPhotoData() = repository.getAllPhotoData()
}