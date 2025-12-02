package com.example.huertohogarmovil.data.remote.api

import com.example.huertohogarmovil.data.remote.dto.DummyCartDto
import com.example.huertohogarmovil.data.remote.dto.DummyCartRequest
import retrofit2.http.*

interface OrdenApiService {

    @GET("carts")
    suspend fun getCarts(): List<DummyCartDto>

    @GET("carts/{id}")
    suspend fun getCart(@Path("id") id: Int): DummyCartDto

    @POST("carts/add")
    suspend fun createCart(@Body body: DummyCartRequest): DummyCartDto
}
