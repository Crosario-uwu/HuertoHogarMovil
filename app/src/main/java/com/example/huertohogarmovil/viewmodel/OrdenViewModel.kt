package com.example.huertohogarmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarmovil.data.repository.CarritoRepository
import com.example.huertohogarmovil.data.repository.OrdenRepository
import com.example.huertohogarmovil.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdenViewModel(
    private val ordenRepository: OrdenRepository,
    private val carritoRepository: CarritoRepository
) : ViewModel() {


    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _ordenCreadaId = MutableStateFlow<Long?>(null)
    val ordenCreadaId: StateFlow<Long?> = _ordenCreadaId.asStateFlow()

    private val _historial = MutableStateFlow<List<Orden>>(emptyList())
    val historial: StateFlow<List<Orden>> = _historial.asStateFlow()
    // =====================================================
// MANEJO DE ERROR DESDE UI (CheckoutScreen lo usa)
// =====================================================
    fun setError(msg: String?) {
        _error.value = msg
    }


    // =====================================================
    // 🚀 CREAR ORDEN (Checkout)
    // =====================================================
    fun crearOrden(
        userId: Long,
        datosEnvio: DatosEnvio,
        metodoPago: MetodoPago,
        tarjeta: CardInfo?
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                // Obtener carrito DESDE ROOM (tu repo lo tiene)
                val itemsCarrito = carritoRepository.obtenerCarritoLocal()

                if (itemsCarrito.isEmpty()) {
                    _error.value = "El carrito está vacío"
                    _loading.value = false
                    return@launch
                }

                val itemsConvertidos = itemsCarrito.map {
                    CartItem(
                        producto = Producto(
                            id = 0, // No lo necesitas para orden
                            title = it.name,
                            category = "General",
                            price = it.price,
                            stock = 0,
                            description = "",
                            thumbnail = it.imageName
                        ),
                        quantity = it.quantity
                    )
                }

                // Crear orden REAL + guardar en Room
                val ordenId = ordenRepository.crearOrden(
                    userId = userId,
                    datosEnvio = datosEnvio,
                    metodoPago = metodoPago,
                    cardInfo = tarjeta,
                    items = itemsConvertidos
                )

                _ordenCreadaId.value = ordenId

            } catch (e: Exception) {
                _error.value = "Error al crear orden: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }


    // =====================================================
    // 🔍 Obtener historial de órdenes por usuario
    // =====================================================
    fun cargarHistorial(userId: Long) {
        viewModelScope.launch {
            _loading.value = true

            try {

                val listaEntities = ordenRepository.obtenerOrdenesUsuario(userId)

                _historial.value = listaEntities.map { entity ->
                    Orden(
                        id = entity.id,
                        userId = entity.userId,
                        items = emptyList(), // Si quieres items reales, se añade luego
                        datosEnvio = DatosEnvio(
                            street = entity.direccion.street,
                            addressDetail = entity.direccion.addressDetail,
                            city = entity.direccion.city,
                            comuna = entity.direccion.comuna
                        ),
                        paymentMethod = entity.paymentMethod,
                        cardInfo = null,
                        status = entity.status,
                        createdAt = entity.createdAt,
                        costoEnvio = entity.costoEnvio
                    )
                }

            } catch (e: Exception) {
                _error.value = "Error al cargar historial: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }


    // =====================================================
    // Limpiar estado tras compra
    // =====================================================
    fun limpiarEstadoOrden() {
        _ordenCreadaId.value = null
        _error.value = null
    }
}
