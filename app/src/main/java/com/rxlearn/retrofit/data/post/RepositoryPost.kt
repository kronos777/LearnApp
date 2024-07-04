package com.rxlearn.retrofit.data.post

import com.rxlearn.retrofit.data.news.JsonPlaceHolderApi

class RepositoryPost(val api: JsonPlaceHolderPostApi) {

    suspend fun gelAllPost() = api.getAllPosts()
    suspend fun gelPostById(id: Int) = api.getPostById(id)
    suspend fun gelPostsByFilter(title: String, order: String) = api.getPostsByFilter(title, order)

}