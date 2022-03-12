package com.zhengdianfang.atrs.repository.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val INSTANCE = Retrofit.Builder().baseUrl("https://24150168-a5f0-4c5a-a0eb-f975f063e847.mock.pstmn.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}