package com.example.huertohogarmovil.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrito")
data class CarritoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productCode: String,
    val name: String,
    val price: Int,
    val quantity: Int,
    val imageName: String
)
