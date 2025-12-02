package com.example.huertohogarmovil.model

data class Producto(
    val id: Int,           // ← coincide con API
    val title: String,     // ← coincide con API
    val category: String,
    val price: Int,
    val stock: Int,
    val description: String,
    val thumbnail: String?  // ← coincide con API
)
