package com.example.huertohogarapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SelectorImagenViewModel : ViewModel() {
    // Guarda la ruta o URI de la imagen seleccionada
    private val _imagenSeleccionada = MutableStateFlow<String?>(null)
    val imagenSeleccionada: StateFlow<String?> = _imagenSeleccionada

    // Cambia la imagen seleccionada
    fun onSeleccionarImagen(uri: String) {
        _imagenSeleccionada.update { uri }
    }

    // Limpia la selección (por si el usuario quiere cambiar o eliminar)
    fun limpiarSeleccion() {
        _imagenSeleccionada.update { null }
    }
}
