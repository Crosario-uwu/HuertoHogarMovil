package com.example.huertohogarmovil.data.repository

import com.example.huertohogarmovil.data.local.entity.EnvioEntity
import com.example.huertohogarmovil.model.DatosEnvio

fun DatosEnvio.toEntity(): EnvioEntity {
    return EnvioEntity(
        street = street,
        addressDetail = addressDetail,
        city = city,
        comuna = comuna
    )
}
