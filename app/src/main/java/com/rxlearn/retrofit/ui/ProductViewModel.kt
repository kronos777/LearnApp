package com.rxlearn.retrofit.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rxlearn.retrofit.data.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(): ViewModel() {
    val user = MutableLiveData<User>()
}