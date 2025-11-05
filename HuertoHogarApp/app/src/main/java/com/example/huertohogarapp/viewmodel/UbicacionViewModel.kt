package com.example.huertohogarapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UbicacionViewModel : ViewModel() {
    // Guarda coordenadas de ubicación (latitud / longitud)
    private val _ubicacion = MutableStateFlow<Pair<Double, Double>?>(null)
    val ubicacion: StateFlow<Pair<Double, Double>?> = _ubicacion

    // Guarda la dirección leída (por ejemplo, de geolocalización)
    private val _direccion = MutableStateFlow<String?>(null)

    val direccion: StateFlow<String?> = _direccion

    // Actualiza la ubicación (por GPS o selección manual)
    fun actualizarUbicacion(lat: Double, lon: Double) {
        _ubicacion.update { lat to lon }
    }

    // Actualiza la dirección textual
    fun actualizarDireccion(nuevaDireccion: String) {
        _direccion.update { nuevaDireccion }
    }

    // Limpia los datos de ubicación
    fun limpiarUbicacion() {
        _ubicacion.update { null }
        _direccion.update { null }
    }
}
