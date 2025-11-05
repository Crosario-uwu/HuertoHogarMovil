package com.example.huertohogarapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.huertohogarapp.model.ItemCarrito
import com.example.huertohogarapp.model.Producto
import com.example.huertohogarapp.repository.CarritoRepository
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel que maneja el estado del carrito de compras.
 * Usa CarritoRepository como fuente de datos.
 */
class CarritoViewModel(
    private val carritoRepository: CarritoRepository = CarritoRepository()
) : ViewModel() {

    // Lista de ítems del carrito expuesta a la UI
    val items: StateFlow<List<ItemCarrito>> = carritoRepository.items

    /** Agrega 1 unidad de un producto al carrito */
    fun agregar(producto: Producto) {
        carritoRepository.agregarProducto(producto)
    }

    /** Cambia la cantidad de un producto específico */
    fun cambiarCantidad(productoId: String, cantidad: Int) {
        carritoRepository.cambiarCantidad(productoId, cantidad)
    }

    /** Elimina un producto del carrito */
    fun eliminar(productoId: String) {
        carritoRepository.eliminarProducto(productoId)
    }

    /** Vacía todo el carrito */
    fun vaciar() {
        carritoRepository.vaciarCarrito()
    }

    /** Total del carrito en CLP */
    fun total(): Int = carritoRepository.total()

    /** Indica si el carrito tiene o no productos */
    fun tieneItems(): Boolean = carritoRepository.tieneItems
}
