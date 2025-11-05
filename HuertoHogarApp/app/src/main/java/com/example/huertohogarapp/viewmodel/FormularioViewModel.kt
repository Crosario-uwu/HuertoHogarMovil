package com.example.huertohogarapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.huertohogarapp.model.ErroresFormulario
import com.example.huertohogarapp.model.FormularioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class FormularioViewModel : ViewModel() {

    // Estado del formulario (nombre, correo, errores, enviado)
    private val _uiState = MutableStateFlow(FormularioUiState())
    val uiState: StateFlow<FormularioUiState> = _uiState

    // 🔹 Actualiza el campo "nombre"
    fun onNombreChange(nuevo: String) {
        _uiState.update { it.copy(nombre = nuevo) }
    }

    // 🔹 Actualiza el campo "correo"
    fun onCorreoChange(nuevo: String) {
        _uiState.update { it.copy(correo = nuevo) }
    }

    // 🔹 Marca si el formulario fue enviado
    fun onEnviarFormulario() {
        _uiState.update {
            it.copy(
                enviado = true,
                errores = validarCampos(it.nombre, it.correo)
            )
        }
    }

    // 🔹 Valida los datos y devuelve errores si existen
    private fun validarCampos(nombre: String, correo: String): ErroresFormulario {
        var errorNombre: String? = null
        var errorCorreo: String? = null

        if (nombre.isBlank()) {
            errorNombre = "El nombre no puede estar vacío"
        }

        if (correo.isBlank() || !correo.contains("@")) {
            errorCorreo = "Correo inválido"
        }

        return ErroresFormulario(
            nombre = errorNombre,
            correo = errorCorreo
        )
    }
}
