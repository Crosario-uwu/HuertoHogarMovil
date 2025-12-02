package com.example.huertohogarmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarmovil.data.local.entity.CarritoEntity
import com.example.huertohogarmovil.data.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarritoViewModel(
    private val repo: CarritoRepository
) : ViewModel() {

    private val _carrito = MutableStateFlow<List<CarritoEntity>>(emptyList())
    val carrito: StateFlow<List<CarritoEntity>> = _carrito


    private val _total = MutableStateFlow(0)
    val total: StateFlow<Int> = _total

    private val _items = MutableStateFlow<List<CarritoEntity>>(emptyList())
    val items: StateFlow<List<CarritoEntity>> = _items

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    val costoEnvio = 3990




    // --------------------------------------------------------
    // CARGAR CARRITO DESDE API + ROOM
    // --------------------------------------------------------
    fun cargarCarrito(userId: Int) {
        viewModelScope.launch {
            try {
                _loading.value = true

                repo.cargarCarritoDesdeApi(userId)

                _items.value = repo.obtenerCarritoLocal()

            } catch (e: Exception) {
                _error.value = "Error al cargar carrito: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // --------------------------------------------------------
    // AGREGAR PRODUCTO (Local + Api)
    // --------------------------------------------------------
    fun agregarProducto(entity: CarritoEntity, userId: Int) {
        viewModelScope.launch {
            try {
                repo.agregarProducto(userId, entity)
                _items.value = repo.obtenerCarritoLocal()

            } catch (e: Exception) {
                _error.value = "Error al agregar: ${e.message}"
            }
        }
    }

    // --------------------------------------------------------
    // ACTUALIZAR CANTIDAD (Solo Room)
    // --------------------------------------------------------
    fun actualizarCantidad(productCode: String, cantidad: Int) {
        viewModelScope.launch {
            try {
                repo.actualizarCantidad(productCode, cantidad)
                _items.value = repo.obtenerCarritoLocal()

            } catch (e: Exception) {
                _error.value = "Error al actualizar: ${e.message}"
            }
        }
    }

    // --------------------------------------------------------
    // LIMPIAR CARRITO
    // --------------------------------------------------------
    fun limpiar() {
        viewModelScope.launch {
            repo.limpiar()
            _items.value = emptyList()
        }
    }

    // --------------------------------------------------------
    // TOTAL CALCULADO CORRECTO
    // --------------------------------------------------------
    fun total(): Int {
        val subtotal = _items.value.sumOf { it.price * it.quantity }
        return subtotal + costoEnvio
    }
}
