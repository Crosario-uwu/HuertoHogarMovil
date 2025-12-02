package com.example.huertohogarmovil.data.remote.dto

data class DummyLoginResponse(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val image: String,
    val token: String
)
