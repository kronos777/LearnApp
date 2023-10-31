package com.example.retrofit.ui.wifi

import android.util.Log
import javax.inject.Inject


class WiFiSettings @Inject constructor() {
    fun openConnection() {
        Log.d("MyLog", "connected")
    }
    fun writeBytes() {
        Log.d("MyLog", "write")
    }
}