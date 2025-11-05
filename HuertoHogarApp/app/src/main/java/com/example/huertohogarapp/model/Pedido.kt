package com.example.huertohogarapp.model

import java.time.LocalDateTime

/**
 * Pedido realizado por un usuario.
 */
data class Pedido(
    val id: String = "",
    val usuarioId: String = "",
    val items: List<ItemCarrito> = emptyList(),
    val total: Int = 0,
    val direccionEntrega: DireccionEntrega? = null,
    val fecha: LocalDateTime = LocalDateTime.now(),
    val estado: EstadoPedido = EstadoPedido.PREPARANDO
)

/**
 * Estados posibles de un pedido.
 */
enum class EstadoPedido {
    PREPARANDO,
    EN_CAMINO,
    ENTREGADO,
    CANCELADO
}
