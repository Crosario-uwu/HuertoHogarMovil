package com.example.huertohogarmovil.data.remote.dto

data class DummyCartDto(
    val id: Int,
    val products: List<DummyCartProduct>,
    val total: Int,
    val discountedTotal: Int,
    val userId: Int,
    val totalProducts: Int,
    val totalQuantity: Int
)

data class DummyCartProduct(
    val id: Int,
    val title: String,
    val price: Int,
    val quantity: Int,
    val total: Int,
    val discountPercentage: Double,
    val discountedPrice: Int
)
