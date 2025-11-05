package com.example.huertohogarapp.model

/**
 * Dirección de entrega asociada a un usuario o pedido.
 */
data class DireccionEntrega(
    val calle: String = "",
    val numero: String = "",
    val comuna: String = "",
    val ciudad: String = "",
    val region: String = "",
    val referencia: String = ""
)





