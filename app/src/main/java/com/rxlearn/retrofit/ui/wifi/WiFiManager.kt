package com.rxlearn.retrofit.ui.wifi

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WiFiManager @Inject constructor(private val settings: WiFiSettings) {
    fun connect(){
        settings.openConnection()
    }
    fun sendMessage(){
        settings.writeBytes()
    }
}