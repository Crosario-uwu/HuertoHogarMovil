package com.example.huertohogarmovil.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orden_items")
data class OrdenItemEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val ordenId: Long,
    val productId: Int,
    val productName: String,
    val price: Int,
    val quantity: Int,
    val imageName: String
)
