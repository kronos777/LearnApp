package com.example.retrofit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofit.data.crypto.CoinNamesListDto
import com.example.retrofit.data.crypto.RepositoryCrypto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(val repositoryCrypto: RepositoryCrypto): ViewModel() {

    private val _coinNamesListDtoLiveData = MutableLiveData<CoinNamesListDto>()
    val coinNamesListDtoLiveData: LiveData<CoinNamesListDto>
        get() = _coinNamesListDtoLiveData

    suspend fun getCoinInfo(int: Int) {
      viewModelScope.launch {
          _coinNamesListDtoLiveData.value = repositoryCrypto.getTopCoinsInfo(int)
      }
    }


}