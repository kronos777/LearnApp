package com.rxlearn.retrofit.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.rxlearn.retrofit.R
import com.rxlearn.retrofit.data.news.PhotoData
import com.rxlearn.retrofit.data.news.UserData
import com.rxlearn.retrofit.data.post.Post
import com.rxlearn.retrofit.data.product.RepositoryProduct
import com.rxlearn.retrofit.data.user.User
import com.rxlearn.retrofit.data.weather.RepositoryWeather
import com.rxlearn.retrofit.databinding.FragmentMainBinding
import com.rxlearn.retrofit.ui.MainViewModel
import com.rxlearn.retrofit.ui.PhotoDataAdapter
import com.rxlearn.retrofit.ui.PostListAdapter
import com.rxlearn.retrofit.ui.ProductAdapter
import com.rxlearn.retrofit.ui.ProductViewModel
import com.rxlearn.retrofit.ui.UserDataAdapter
import com.rxlearn.retrofit.ui.wifi.WiFiManager
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
class MainFragment : Fragment() {

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

    private lateinit var binding: FragmentMainBinding

    private val navController: NavController by lazy {
        (activity?.supportFragmentManager?.findFragmentById(R.id.fragment_item_container) as NavHostFragment).navController
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        val argsUser: String? = args.getString(CURRENT_USER)
        val gson = Gson()
        user = gson.fromJson(argsUser.toString(), User::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewElement()
        listenerBackPressed()
    }

    private fun initViewElement() {
        wiFiManager.connect()
        progressBar = binding.progressBar
        recyclerView = binding.recyclerView
        errorMsg = binding.errorMsg

        onlyShowProgressBar()

      //  val repo = Repository(JsonPlaceHolderSingleton.api)
       // val vmFactory = MainViewModelFactory(repo)
       // viewModel = ViewModelProviders.of(this, vmFactory).get(MainViewModel::class.java)
       // viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModelProduct.user.observe(requireActivity()) {
            Log.d("currentUser", it.firstName)
        }

        clickButton()
    }

    private fun clickButton() {
        val buttonShowUserData = binding.showUsers
        val buttonShowPhotoData = binding.showPhoto
        val buttonShowProductData = binding.showProduct
        val buttonShowPostData = binding.showPost
        val searchBar = binding.searchBar
        buttonShowProductData.setOnClickListener {
            //showProductData()
            showProductsData()
            //showWeatherData()
        }
        buttonShowUserData.setOnClickListener {
            showUserData()
        }
        buttonShowPhotoData.setOnClickListener {
            showPhotoData()
        }
        buttonShowPostData.setOnClickListener {
            showPostData()
        }

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
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
            ).let { model ->
                CoroutineScope(Dispatchers.Main).launch {
                    errorMsg.text = model.location.name
                    errorMsg.text = model.current.temp_c.toString()
                }
            }
        }
    }

    private fun showProductsDataByName(name: String?) {
        progressBar.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {
            val products = name?.let { repositoryProduct.getProductsByName(user.token, it) }
            CoroutineScope(Dispatchers.Main).launch {
                onlyShowRecyclerView()
                val adapter = ProductAdapter()
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
                if (products != null) {
                    adapter.submitList(products.products)
                } else {
                    // Toast.makeText(this, "text query is empty", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
/*
    private fun initRecyclerView(userDataList: List<UserData>) {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = UserDataAdapter(userDataList)
        }
    }
* */
    private fun setupRecyclerViewPostData(postDataList: List<Post>) {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = PostListAdapter(postDataList)
        }
       // setupClickListener()
    }
    private fun showProductsData() {
        progressBar.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {
            val products = viewModel.gelAllProducts()
           // Log.d("allProducts", products.products.toString())
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                onlyShowRecyclerView()
                val adapter = ProductAdapter()
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
                adapter.submitList(products.products)
            }
        }/**/
    }
    private fun showPostData() {
        viewModel.gelAllPost.observe(viewLifecycleOwner) { posts ->
            //Log.d("allPostsData", posts.toString())
            onlyShowRecyclerView()
            setupRecyclerViewPostData(posts.posts)
        }
    }
    private fun showProductData() {
        progressBar.visibility = View.VISIBLE
        errorMsg.text = ""
        CoroutineScope(Dispatchers.IO).launch {
            val product = repositoryProduct.getProductById(5)
            CoroutineScope(Dispatchers.Main).launch {
             //   title = product.title
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
            layoutManager = LinearLayoutManager(requireContext())
            adapter = UserDataAdapter(userDataList)
        }
    }

    private fun initRecyclerViewPhoto(photoDataList: List<PhotoData>) {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = PhotoDataAdapter(photoDataList)
        }
    }

    private fun listenerBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
           // navController.popBackStack(R.id.mainFragment, true)
            //navController.navigate()
            activity?.finishAffinity()
        }
    }
    companion object {
        const val CURRENT_USER = "currentUser"
    }
}