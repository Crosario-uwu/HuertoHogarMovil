package com.example.huertohogarapp.repository

import com.example.huertohogarapp.model.ItemCarrito
import com.example.huertohogarapp.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Maneja el carrito de compras.
 * Cumple "almacenamiento local" mientras la app está abierta.
 * Luego puedes persistir a DataStore / SharedPreferences.
 */
class CarritoRepository {
    private val _items = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val items: StateFlow<List<ItemCarrito>> = _items

    fun agregarProducto(producto: Producto) {
        _items.update { lista ->

            val existente = lista.find { it.producto.id == producto.id }
            if (existente == null) {
                lista + ItemCarrito(producto, 1)
            } else {
                lista.map {
                    if (it.producto.id == producto.id)
                        it.copy(cantidad = it.cantidad + 1)
                    else
                        it
                }
            }
        }
    }

    fun cambiarCantidad(productoId: String, nuevaCantidad: Int) {
        _items.update { lista ->
            lista.map {
                if (it.producto.id == productoId)
                    it.copy(cantidad = nuevaCantidad.coerceAtLeast(1))
                else
                    it
            }
        }
    }

    fun eliminarProducto(productoId: String) {
        _items.update { lista ->
            lista.filterNot { it.producto.id == productoId }
        }
    }

    fun vaciarCarrito() {
        _items.value = emptyList()
    }

    fun total(): Int = _items.value.sumOf { it.subtotal }

    val tieneItems: Boolean
        get() = _items.value.isNotEmpty()

    // TODO local real:
    // - Guardar y cargar _items usando DataStore o SharedPreferences
}
