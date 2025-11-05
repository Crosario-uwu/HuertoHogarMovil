package com.example.huertohogarapp.repository

import com.example.huertohogarapp.model.DireccionEntrega
import com.example.huertohogarapp.model.EstadoPedido
import com.example.huertohogarapp.model.ItemCarrito
import com.example.huertohogarapp.model.Pedido
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

/**
 * Maneja la creación y el historial de pedidos.
 */
class PedidoRepository {
    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())

    val pedidos: StateFlow<List<Pedido>> = _pedidos

    fun crearPedido(
        usuarioId: String,
        items: List<ItemCarrito>,
        direccionEntrega: DireccionEntrega?
    ): Pedido {
        val total = items.sumOf { it.subtotal }
        val nuevo = Pedido(
            id = System.currentTimeMillis().toString(),
            usuarioId = usuarioId,
            items = items,
            total = total,
            direccionEntrega = direccionEntrega,
            fecha = LocalDateTime.now(),
            estado = EstadoPedido.PREPARANDO
        )

        _pedidos.update { it + nuevo }
        return nuevo
    }

    fun getPedidosDeUsuario(usuarioId: String): List<Pedido> =
        _pedidos.value.filter { it.usuarioId == usuarioId }

    fun actualizarEstadoPedido(pedidoId: String, nuevoEstado: EstadoPedido) {
        _pedidos.update { lista ->
            lista.map {
                if (it.id == pedidoId) it.copy(estado = nuevoEstado) else it
            }
        }
    }

    // TODO Firebase:
    // - Guardar cada pedido en colección "pedidos"
    // - Filtrar por usuarioId en Firestore
}
