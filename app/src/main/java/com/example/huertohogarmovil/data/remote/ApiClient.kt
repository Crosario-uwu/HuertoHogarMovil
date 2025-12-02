package com.example.huertohogarmovil.data.remote

import com.example.huertohogarmovil.data.remote.api.DummyJsonProductApi
import com.example.huertohogarmovil.data.remote.api.UserApiService
import com.example.huertohogarmovil.data.remote.api.OrdenApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://dummyjson.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productApi: DummyJsonProductApi by lazy {
        retrofit.create(DummyJsonProductApi::class.java)
    }

    val userApi: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }

    val ordenApi: OrdenApiService by lazy {
        retrofit.create(OrdenApiService::class.java)
    }
}
