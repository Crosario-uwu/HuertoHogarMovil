package com.example.huertohogarapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.model.DireccionEntrega
import com.example.huertohogarapp.model.Pedido
import com.example.huertohogarapp.repository.CarritoRepository
import com.example.huertohogarapp.repository.PedidoRepository
import com.example.huertohogarapp.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PedidoViewModel(private val pedidoRepository: PedidoRepository = PedidoRepository(),
           private val usuarioRepository: UsuarioRepository = UsuarioRepository(),
           private val carritoRepository: CarritoRepository = CarritoRepository()
) : ViewModel() {
    private val _ultimoPedido = MutableStateFlow<Pedido?>(null)
    val ultimoPedido: StateFlow<Pedido?> = _ultimoPedido

    val pedidosUsuarioActual: StateFlow<List<Pedido>> =
        pedidoRepository.pedidos.map { lista ->
            val usuarioId = usuarioRepository.getUsuarioActual()?.id
            if (usuarioId == null) emptyList() else lista.filter { it.usuarioId == usuarioId }
        } as StateFlow<List<Pedido>> // ojo: para producción sería mejor usar stateIn

    fun confirmarPedido(direccion: DireccionEntrega?) {
        val usuario = usuarioRepository.getUsuarioActual() ?: return
        val items = carritoRepository.items.value
        if (items.isEmpty()) return

        viewModelScope.launch {
            val pedido = pedidoRepository.crearPedido(
                usuarioId = usuario.id,
                items = items,
                direccionEntrega = direccion
            )
            _ultimoPedido.value = pedido
            carritoRepository.vaciarCarrito()
        }
    }
}
