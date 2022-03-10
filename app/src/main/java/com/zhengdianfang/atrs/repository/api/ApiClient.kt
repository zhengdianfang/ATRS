package com.zhengdianfang.atrs.repository.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val INSTANCE = Retrofit.Builder().baseUrl("https://mobile-bff/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}