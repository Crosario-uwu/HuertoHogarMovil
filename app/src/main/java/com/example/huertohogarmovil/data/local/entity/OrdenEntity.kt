package com.example.huertohogarmovil.data.local.entity

import androidx.room.*
import com.example.huertohogarmovil.model.CardInfo
import com.example.huertohogarmovil.model.MetodoPago
import com.example.huertohogarmovil.model.OrdenStatus

@Entity(tableName = "ordenes")
data class OrdenEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    @Embedded(prefix = "dir_") val direccion: EnvioEntity,
    val paymentMethod: MetodoPago,
    val cardNumber: String?,
    val cardHolder: String?,
    val createdAt: String,
    val costoEnvio: Int = 3990,
    val status: OrdenStatus = OrdenStatus.PENDIENTE,
    val cardInfo: CardInfo?           // ← ESTE CAMPO ES EL PROBLEMA
)
