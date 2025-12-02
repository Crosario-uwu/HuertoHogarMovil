package com.example.huertohogarmovil.model

data class CardInfo(
    val cardNumber: String,
    val expiryDate: String,   // MM/YY
    val cvv: String,
    val cardHolder: String
)