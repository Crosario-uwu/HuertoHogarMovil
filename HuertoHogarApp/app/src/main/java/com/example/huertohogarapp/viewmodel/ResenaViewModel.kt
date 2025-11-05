package com.example.huertohogarapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.huertohogarapp.model.Resena
import com.example.huertohogarapp.repository.ResenaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class ResenaViewModel(
    private val resenaRepository: ResenaRepository = ResenaRepository()
) : ViewModel() {

    val resenas: StateFlow<List<Resena>> = resenaRepository.resenas

    fun agregarResena(
        productoId: String,
        usuarioNombre: String,
        comentario: String,
        rating: Int
    ) {
        resenaRepository.agregarResena(productoId, usuarioNombre, comentario, rating)
    }
    fun resenasDeProducto(productoId: String): StateFlow<List<Resena>> {
        val flow = MutableStateFlow<List<Resena>>(emptyList())
        // Versión simple: filtramos cada vez que se llame
        flow.value = resenaRepository.getResenasDeProducto(productoId)
        return flow
    }

    fun promedioProducto(productoId: String): Double =
        resenaRepository.getPromedioRating(productoId)
}
