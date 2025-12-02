package com.example.huertohogarmovil.model

data class CartItem(
    val producto: Producto,
    val quantity: Int
) {
    val subtotal: Int get() = producto.price * quantity
}
