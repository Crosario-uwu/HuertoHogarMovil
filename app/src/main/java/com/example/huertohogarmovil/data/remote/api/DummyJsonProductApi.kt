package com.example.huertohogarmovil.data.remote.api

import com.example.huertohogarmovil.data.remote.dto.DummyProductDto
import com.example.huertohogarmovil.data.remote.dto.DummyProductListResponse
import retrofit2.http.*

interface DummyJsonProductApi {

    @GET("products")
    suspend fun getProducts(): DummyProductListResponse

    @GET("products/{id}")
    suspend fun getProductid(@Path("id") id: Int): DummyProductDto

    @POST("products/add")
    suspend fun createProduct(@Body body: Map<String, Any>): DummyProductDto

    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body body: Map<String, Any>
    ): DummyProductDto

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int)
}
