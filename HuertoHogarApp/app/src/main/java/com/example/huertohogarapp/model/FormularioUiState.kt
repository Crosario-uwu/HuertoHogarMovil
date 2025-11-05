package com.example.huertohogarapp.model

data class FormularioUiState(
    val nombre: String = "",
    val correo: String = "",
    val errores: ErroresFormulario = ErroresFormulario(),
    val enviado: Boolean = false              // indica si el formulario fue enviado
)

