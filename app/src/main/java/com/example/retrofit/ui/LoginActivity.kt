package com.example.retrofit.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit.R
import com.example.retrofit.data.product.RepositoryProduct
import com.example.retrofit.data.user.AuthRequest
import com.example.retrofit.data.user.User
import com.example.retrofit.databinding.ActivityLogin2Binding
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLogin2Binding

    @Inject
    lateinit var repositoryProduct: RepositoryProduct

    private val viewModelProduct: ProductViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = binding.button
        button.setOnClickListener {
            auth()
        }
    }

    fun auth() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = repositoryProduct.auth(AuthRequest(
               /* binding.firstName.toString(),
                binding.lastName.toString()*/
                "ggude7",
                "MWwlaeWcOoF6"
            ))
            runOnUiThread {
                Picasso.get().load(user.image).into(binding.iv)
                binding.firstName.text = user.firstName
                binding.lastName.text = user.lastName
               // viewModelProduct.user.value = user
            }
            delay(5000)
            //gmain()
            goMain(user)

        }

    }

    private fun gmain() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
    private fun goMain(user: User) {
        val intent = Intent(this, MainActivity::class.java)
        var gson = Gson()
        var jsonString = gson.toJson(user)
        intent.putExtra("user", jsonString)
        startActivity(intent)
    }

}