package com.example.huertohogarmovil.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class ProductoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val category: String,
    val price: Int,
    val stock: Int,
    val description: String,
    val thumbnail: String? = null // ← guardamos URI de la galería
)


