package com.example.huertohogarapp.repository

import com.example.huertohogarapp.model.Resena
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Maneja reseñas y calificaciones de productos.
 */
class ResenaRepository {
    private val _resenas = MutableStateFlow<List<Resena>>(emptyList())
    val resenas: StateFlow<List<Resena>> = _resenas

    fun agregarResena(
        productoId: String,
        usuarioNombre: String,
        comentario: String,
        rating: Int
    ): Resena {
        val nueva = Resena(
            id = System.currentTimeMillis().toString(),
            productoId = productoId,
            usuarioNombre = usuarioNombre,
            comentario = comentario,
            rating = rating.coerceIn(1, 5),
            fecha = "" // podrías guardar LocalDateTime como String
        )
        _resenas.update { it + nueva }
        return nueva
    }

    fun getResenasDeProducto(productoId: String): List<Resena> =
        _resenas.value.filter { it.productoId == productoId }

    fun getPromedioRating(productoId: String): Double {
        val lista = getResenasDeProducto(productoId)
        if (lista.isEmpty()) return 0.0
        return lista.map { it.rating }.average()
    }

    // TODO Firebase:
    // - Guardar reseñas en colección "resenas"
    // - Consultar por productoId
}
