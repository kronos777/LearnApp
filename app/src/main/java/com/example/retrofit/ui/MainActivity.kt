package com.example.retrofit.ui

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
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.R
import com.example.retrofit.data.news.PhotoData
import com.example.retrofit.data.news.UserData
import com.example.retrofit.data.product.Product
import com.example.retrofit.data.product.RepositoryProduct
import com.example.retrofit.data.user.User
import com.example.retrofit.data.weather.RepositoryWeather
import com.example.retrofit.ui.wifi.WiFiManager
import com.google.gson.Gson
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

    @Inject
    lateinit var wiFiManager: WiFiManager


    @Inject
    lateinit var repositoryProduct: RepositoryProduct

    @Inject
    lateinit var repositoryWeather: RepositoryWeather


    private val viewModel: MainViewModel by viewModels()
    private val viewModelProduct: ProductViewModel by viewModels()


    private val compositeDisposable = CompositeDisposable()

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorMsg: TextView
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arguments = intent.extras
        val dataUserString = arguments?.get("user")
        var gson = Gson()
        user = gson.fromJson(dataUserString.toString(), User::class.java)
        Toast.makeText(this, "currentUser" + user, Toast.LENGTH_SHORT).show()

        wiFiManager.connect()

        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        errorMsg = findViewById<TextView>(R.id.errorMsg)

        onlyShowProgressBar()

       // val repo = Repository(JsonPlaceHolderSingleton.api)
       // val vmFactory = MainViewModelFactory(repo)
       // viewModel = ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java)
        //viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        /*viewModelProduct.user.observe(this) {
            Log.d("currentUser", it.firstName)
        }*/

        clickButton()

    }


    private fun clickButton() {
        val buttonShowUserData = findViewById<Button>(R.id.show_users)
        val buttonShowPhotoData = findViewById<Button>(R.id.show_photo)
        val buttonShowProductData = findViewById<Button>(R.id.show_product)
        val searchBar = findViewById<androidx.appcompat.widget.SearchView>(R.id.search_bar)
        buttonShowProductData.setOnClickListener {
            //showProductData()
            //showProductsData()
            showWeatherData()
        }
        buttonShowUserData.setOnClickListener {
            showUserData()
        }
        buttonShowPhotoData.setOnClickListener {
            showPhotoData()
        }

        searchBar.setOnQueryTextListener(object : OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(txt: String?): Boolean {
                showProductsDataByName(txt)
                return true
            }

            override fun onQueryTextChange(txt: String?): Boolean {
              //  showProductsDataByName(txt)
                return true
            }
        })


    }

    private fun showWeatherData() {
        progressBar.visibility = View.GONE
        errorMsg.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val model = repositoryWeather.getDataWeather(
                "57081927e320458189d164829230111",
                "Moscow",
                "3",
                "no",
                "no"
            )
            runOnUiThread {
                errorMsg.text = model.location.name.toString()
                errorMsg.text = model.current.temp_c.toString()
            }
        }
    }

    private fun showProductsDataByName(name: String?) {
        progressBar.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {
            val products = name?.let { repositoryProduct.getProductsByName(user.token, it) }
            runOnUiThread {
                onlyShowRecyclerView()
                val adapter = ProductAdapter()
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.adapter = adapter
                if (products != null) {
                    adapter.submitList(products.products)
                } else {
                   // Toast.makeText(this, "text query is empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun showProductsData() {
        progressBar.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {
            val products = repositoryProduct.getAllProducts()
            runOnUiThread {
                onlyShowRecyclerView()
                val adapter = ProductAdapter()
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerView.adapter = adapter
                adapter.submitList(products.products)
            }
        }
    }

    private fun showProductData() {
      progressBar.visibility = View.VISIBLE
        errorMsg.text = ""
        CoroutineScope(Dispatchers.IO).launch {
            val product = repositoryProduct.getProductById(5)
            runOnUiThread {
                title = product.title
                errorMsg.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
                errorMsg.text = product.title
            }
        }
        //sleep(5000)
       // Log.d("thisProduct", "title.toString()")
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

    private fun initRecyclerAllProducts(productsList: List<Product>) {

    }


}