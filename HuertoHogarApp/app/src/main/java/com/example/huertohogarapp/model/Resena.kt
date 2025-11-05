package com.example.huertohogarapp.model

/**
 * Reseña o calificación de un producto.
 */
data class Resena(
    val id: String = "",
    val productoId: String = "",
    val usuarioNombre: String = "",
    val comentario: String = "",
    val rating: Int = 0, // 1..5
    val fecha: String = ""
)

