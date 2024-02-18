package com.example.retrofit.data.crypto

import com.example.retrofit.data.crypto.JsonPlaceHolderCrypto.api

class RepositoryCrypto(api: CryptoApi) {

    suspend fun getTopCoinsInfo(int: Int) = api.getTopCoinsInfo(limit = int)

    suspend fun getFullPriceList(string: String) = api.getFullPriceList(fSyms = string)


}