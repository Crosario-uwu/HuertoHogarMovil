package com.example.huertohogarmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarmovil.data.repository.ProductoRepository
import com.example.huertohogarmovil.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(
    private val repo: ProductoRepository
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos = _productos.asStateFlow()

    private val _productoDetalle = MutableStateFlow<Producto?>(null)
    val productoDetalle = _productoDetalle.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    //----------------------------------------------------------
    init { cargarProductos() }
    //----------------------------------------------------------

    fun cargarProductos() {
        viewModelScope.launch {
            repo.obtenerProductos().collect { lista ->
                _productos.value = lista
            }
        }
    }

    fun cargarDetalleDesdeRoom(id: Int) {
        viewModelScope.launch {
            _productoDetalle.value = repo.obtenerProducto(id)
        }
    }

    fun crearProducto(title: String, price: Int, category: String, description: String) {
        viewModelScope.launch {
            repo.crearProducto(title, price, category, description, 10, "")
        }
    }

    fun actualizarProducto(id: Int, title: String, price: Int) {
        viewModelScope.launch {
            val prod = repo.obtenerProducto(id) ?: return@launch
            repo.actualizarProducto(id, title, price, prod.category, prod.description, prod.stock, prod.thumbnail)
        }
    }

    fun eliminarProducto(id: Int) {
        viewModelScope.launch {
            repo.eliminarProducto(id)
        }
    }
}
