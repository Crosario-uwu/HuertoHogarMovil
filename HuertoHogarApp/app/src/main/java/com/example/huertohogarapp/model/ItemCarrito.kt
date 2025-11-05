package com.example.huertohogarapp.model

/**
 * Elemento individual del carrito.
 */
data class ItemCarrito(
    val producto: Producto,
    val cantidad: Int = 1
) {
    val subtotal: Int get() = producto.precio * cantidad
}


