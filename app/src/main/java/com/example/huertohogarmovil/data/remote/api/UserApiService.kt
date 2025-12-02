package com.example.huertohogarmovil.data.remote.api

import com.example.huertohogarmovil.data.remote.dto.DummyUserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {

    @GET("users")
    suspend fun getUsers(): List<DummyUserDto>

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): DummyUserDto
}
