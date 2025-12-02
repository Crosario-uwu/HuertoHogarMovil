package com.example.huertohogarmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarmovil.data.repository.UserRepository
import com.example.huertohogarmovil.data.repository.ProductoRepository
import com.example.huertohogarmovil.data.repository.OrdenRepository
import com.example.huertohogarmovil.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminViewModel(
private val userRepo: UserRepository,
private val productoRepo: ProductoRepository,
private val ordenRepo: OrdenRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    private val _usuarios = MutableStateFlow<List<User>>(emptyList())
    val usuarios = _usuarios.asStateFlow()

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos = _productos.asStateFlow()

    private val _ordenes = MutableStateFlow<List<Orden>>(emptyList())
    val ordenes = _ordenes.asStateFlow()

    private val _ordenDetalle = MutableStateFlow<Orden?>(null)
    val ordenDetalle = _ordenDetalle.asStateFlow()


    // ============================================================
    // ✔ CARGAR ÓRDENES (MAPPING Entity → Modelo)
    // ============================================================
    fun cargarOrdenes() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val entities = ordenRepo.obtenerOrdenesUsuario(0)



            } catch (e: Exception) {
                _error.value = "Error al cargar órdenes: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // ============================================================
    // ✔ ACTUALIZAR ESTADO
    // ============================================================
    fun actualizarEstado(id: Long, estado: OrdenStatus) {
        viewModelScope.launch {
            try {
                _loading.value = true
                ordenRepo.actualizarEstado(id, estado)
                _mensaje.value = "Estado actualizado"
            } catch (_: Exception) {
                _error.value = "Error al actualizar estado"
            } finally {
                _loading.value = false
            }
        }
    }


    // ============================================================
    // ✔ CARGAR USUARIOS
    // ============================================================
    fun cargarUsuarios() {
        viewModelScope.launch {
            try {
                _loading.value = true
                userRepo.obtenerUsuariosLocal().collect {
                    _usuarios.value = it
                }
            } catch (_: Exception) {
                _error.value = "Error al cargar usuarios"
            } finally {
                _loading.value = false
            }
        }
    }


    // ============================================================
    // ✔ CARGAR PRODUCTOS
    // ============================================================
    fun cargarProductos() {
        viewModelScope.launch {
            try {
                _loading.value = true
                productoRepo.obtenerProductos().collect {
                    _productos.value = it
                }
            } catch (_: Exception) {
                _error.value = "Error al cargar productos"
            } finally {
                _loading.value = false
            }
        }
    }


    fun limpiarMensajes() {
        _error.value = null
        _mensaje.value = null
    }
}
