package com.example.huertohogarmovil.model

data class DatosEnvio(
    val street: String,       // Calle y número
    val addressDetail: String, // Departamento o complemento
    val city: String,
    val comuna: String
)