package com.example.huertohogarmovil.data.remote.dto

data class DummyCartRequest(
    val userId: Int,
    val products: List<CartProductItem>
)

data class CartProductItem(
    val id: Int,
    val quantity: Int
)
