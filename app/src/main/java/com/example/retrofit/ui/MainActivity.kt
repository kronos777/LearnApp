package com.example.retrofit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.R
import com.example.retrofit.data.PhotoData
import com.example.retrofit.data.UserData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private val compositeDisposable = CompositeDisposable()

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorMsg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        errorMsg = findViewById<TextView>(R.id.errorMsg)

        onlyShowProgressBar()

       // val repo = Repository(JsonPlaceHolderSingleton.api)
       // val vmFactory = MainViewModelFactory(repo)
       // viewModel = ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        clickButton()



    }

    private fun clickButton() {
        val buttonShowUserData = findViewById<Button>(R.id.show_users)
        val buttonShowPhotoData = findViewById<Button>(R.id.show_photo)
        buttonShowUserData.setOnClickListener {
            showUserData()
        }
        buttonShowPhotoData.setOnClickListener {
            showPhotoData()
        }
    }


    private fun showPhotoData() {
        compositeDisposable += viewModel.getAllPhotoData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { photoDataList ->
                    Log.e("netError", photoDataList.toString())
                    initRecyclerViewPhoto(photoDataList)
                    onlyShowRecyclerView()
                },
                onError = { e -> onlyShowErrorMsg(e.message!!) }
            )
    }

    private fun showUserData() {
        compositeDisposable += viewModel.getAllUserData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { userDataList ->
                    Log.e("netError", userDataList.toString())
                    initRecyclerView(userDataList)
                    onlyShowRecyclerView()
                },
                onError = { e -> onlyShowErrorMsg(e.message!!) }
            )
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.dispose()
    }

    private fun onlyShowProgressBar() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
        errorMsg.visibility = View.INVISIBLE
    }


    private fun onlyShowRecyclerView() {
        progressBar.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
        errorMsg.visibility = View.INVISIBLE
    }


    private fun onlyShowErrorMsg(msg: String) {
        progressBar.visibility = View.INVISIBLE
        recyclerView.visibility = View.INVISIBLE
        errorMsg.visibility = View.VISIBLE
        errorMsg.text = "Network Error: $msg"
        Log.e("netError", msg)
    }

    private fun initRecyclerView(userDataList: List<UserData>) {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UserDataAdapter(userDataList)
        }
    }

    private fun initRecyclerViewPhoto(photoDataList: List<PhotoData>) {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PhotoDataAdapter(photoDataList)
        }
    }

}