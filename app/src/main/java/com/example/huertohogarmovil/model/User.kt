package com.example.huertohogarmovil.model

data class User(
    val id: Long,
    val email: String,
    val name: String,
    val phone: String?,
    val password: String
)
