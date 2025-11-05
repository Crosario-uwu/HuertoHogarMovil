package com.example.huertohogarapp.model

/**
 * Estado completo del formulario de preferencias Huerto Hogar.
 * Se utiliza en FormularioViewModel y FormularioScreen.
 */
data class FormularioUiState(
    val nombre: String = "",
    val correo: String = "",
    val categoriaFavorita: String = "",
    val volumen: Float = 0.5f,
    val aceptaTerminos: Boolean = false,
    val errores: ErroresFormulario = ErroresFormulario(),
    val enviado: Boolean = false
)
