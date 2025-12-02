package com.example.huertohogarmovil.data.remote.dto

data class DummyProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Int,
    val stock: Int?,
    val thumbnail: String
)
