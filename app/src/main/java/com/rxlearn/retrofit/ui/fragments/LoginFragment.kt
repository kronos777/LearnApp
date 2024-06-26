package com.rxlearn.retrofit.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postAtTime
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import com.rxlearn.retrofit.R
import com.rxlearn.retrofit.data.product.RepositoryProduct
import com.rxlearn.retrofit.data.user.AuthRequest
import com.rxlearn.retrofit.data.user.User
import com.rxlearn.retrofit.databinding.FragmentLoginBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val navController by lazy {
        (activity?.supportFragmentManager?.findFragmentById(R.id.fragment_item_container) as NavHostFragment).navController
    }

    @Inject
    lateinit var repositoryProduct: RepositoryProduct

    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            auth()
        }

    }
    private fun auth() {
        CoroutineScope(Dispatchers.IO).launch {
            repositoryProduct.auth(
                AuthRequest(
                /* binding.firstName.toString(),
                 binding.lastName.toString()*/
                "emilys",
                "emilyspass"
                )
            ).let { user ->
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    Picasso.get().load(user.image).into(binding.iv)
                    binding.firstName.text = user.firstName
                    binding.lastName.text = user.lastName
                    goMain(user)
                }
            }
        }

    }

    private fun goMain(user: User) {
        CoroutineScope(Dispatchers.Main).launch {
            val gson = Gson()
            val jsonString = gson.toJson(user)
            val params = Bundle().apply{
                putString(CURRENT_USER, jsonString)
            }
            delay(5000)
            navController.navigate(R.id.mainFragment, params)
        }
    }

    companion object {
        const val CURRENT_USER = "currentUser"
    }

}