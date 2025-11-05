package com.example.huertohogarapp.model

enum class Categoria(val displayName: String) {
    FRUTAS("Frutas Frescas"),
    VERDURAS("Verduras Orgánicas"),
    ORGANICOS("Productos Orgánicos"),
    LACTEOS("Productos Lácteos")
}

data class Producto(
    val id: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val precio: Int = 0,
    val categoria: Categoria = Categoria.FRUTAS,
    val origen: String = "",
    val stock: Int = 0,
    val imagenUrl: String = "",      // URL de Firebase Storage
    val destacado: Boolean = false   // Para mostrar en portada
)