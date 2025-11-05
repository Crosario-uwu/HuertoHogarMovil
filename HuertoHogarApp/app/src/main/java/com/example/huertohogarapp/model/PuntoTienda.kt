package com.example.huertohogarapp.model

/**
 * Representa una sucursal física de Huerto Hogar.
 */
data class PuntoTienda(
    val id: String = "",
    val nombre: String = "",
    val direccion: String = "",
    val ciudad: String = "",
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val telefono: String = "",
    val horario: String = ""
)
