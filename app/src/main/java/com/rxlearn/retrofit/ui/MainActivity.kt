package com.rxlearn.retrofit.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rxlearn.retrofit.R
import com.rxlearn.retrofit.data.news.PhotoData
import com.rxlearn.retrofit.data.news.UserData
import com.rxlearn.retrofit.data.product.Product
import com.rxlearn.retrofit.data.product.RepositoryProduct
import com.rxlearn.retrofit.data.user.User
import com.rxlearn.retrofit.data.weather.RepositoryWeather
import com.rxlearn.retrofit.ui.wifi.WiFiManager
import com.google.gson.Gson
import com.rxlearn.retrofit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }





}