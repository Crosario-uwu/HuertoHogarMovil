package com.example.huertohogarapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.huertohogarapp.model.Producto
import com.example.huertohogarapp.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel(
    private val productoRepository: ProductoRepository = ProductoRepository()
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos
    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        _productos.value = productoRepository.getProductos()
    }

    fun getProductoPorId(id: String): Producto? = productoRepository.getProductoPorId(id)
}



