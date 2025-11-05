package com.example.huertohogarapp.model

data class Usuario(
    val id: String = "",
    val nombre: String = "",
    val correo: String = "",
    val telefono: String = "",
    val direccion: DireccionEntrega? = null,
    val fotoPerfilUri: String? = null   // Ruta local o URL de Firebase Storage
)