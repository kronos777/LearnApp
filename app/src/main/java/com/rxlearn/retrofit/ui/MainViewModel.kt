package com.rxlearn.retrofit.ui


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rxlearn.retrofit.data.news.Repository
import com.rxlearn.retrofit.data.post.RepositoryPost
import com.rxlearn.retrofit.data.product.RepositoryProduct
import com.rxlearn.retrofit.ui.Utils.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val repositoryProduct: RepositoryProduct,
    private val repositoryPost: RepositoryPost

) : ViewModel() {
    //private val repository: Repository = Repository(JsonPlaceHolderSingleton.api)
    val loadState = MutableLiveData<LoadState>()

    val gelAllPost = flow {
        emit(repositoryPost.gelAllPost())
    }.asLiveData()

    suspend fun gelAllProducts() = repositoryProduct.getAllProducts()
    fun getAllUserData() = repository.getAllUserData()

    fun getAllPhotoData() = repository.getAllPhotoData()



}