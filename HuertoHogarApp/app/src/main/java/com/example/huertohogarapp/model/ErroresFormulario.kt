package com.example.huertohogarapp.model

/**
 * Representa los mensajes de error de validación del formulario.
 */
data class ErroresFormulario(
    val nombre: String? = null,
    val correo: String? = null,
    val categoriaFavorita: String? = null,
    val volumen: String? = null,
    val aceptaTerminos: String? = null
)
