package com.example.huertohogarmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarmovil.data.repository.OrdenRepository
import com.example.huertohogarmovil.data.repository.UserRepository
import com.example.huertohogarmovil.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

class PerfilViewModel(
    private val userRepository: UserRepository,
    private val ordenRepository: OrdenRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _historial = MutableStateFlow<List<Orden>>(emptyList())
    val historial: StateFlow<List<Orden>> = _historial

    // -------------------------------------------------------
    // 🔵 FOTO DE PERFIL (Galería + Cámara) – Guía 13
    // -------------------------------------------------------

    private val _uriImagen = MutableStateFlow<String?>(null)
    val uriImagen: StateFlow<String?> = _uriImagen

    fun actualizarImagenGaleria(uri: String?) {
        _uriImagen.value = uri
    }

    fun actualizarImagenCamara(uri: String?) {
        _uriImagen.value = uri
    }

    // -------------------------------------------------------
    // 🟢 Cargar usuario desde Room → si no existe, API
    // -------------------------------------------------------
    fun cargarUsuario(id: Long) {
        viewModelScope.launch {
            try {
                _loading.value = true

                // 1) Intentar obtener desde Room
                val usuarioLocal = userRepository.obtenerUsuarioLocal(id).first()

                if (usuarioLocal != null) {
                    _user.value = usuarioLocal
                    return@launch
                }

                // 2) Si Room no tiene el usuario → API DummyJson
                val remoto = userRepository.obtenerUsuarioRemoto(id)
                _user.value = remoto

            } catch (e: Exception) {
                _error.value = "Error al cargar usuario: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }


    // -------------------------------------------------------
    // 🟢 Actualizar perfil (solo Room)
    // -------------------------------------------------------
    fun actualizarPerfil(nombre: String, telefono: String?) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val actual = _user.value ?: return@launch

                userRepository.actualizarUsuario(
                    id = actual.id,
                    nombre = nombre,
                    email = actual.email,
                    telefono = telefono
                )

                // Actualizar estado en memoria
                _user.value = actual.copy(
                    name = nombre,
                    phone = telefono
                )

                _mensaje.value = "Perfil actualizado correctamente"

            } catch (e: Exception) {
                _error.value = "Error al actualizar: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }


    // -------------------------------------------------------
    // 🟢 Cargar historial de órdenes
    // -------------------------------------------------------
    fun cargarHistorial(userId: Long) {
        viewModelScope.launch {
            try {
                _loading.value = true

                val entities = ordenRepository.obtenerOrdenesUsuario(userId)

                val lista = entities.map { entity ->
                    Orden(
                        id = entity.id,
                        userId = entity.userId,
                        datosEnvio = DatosEnvio(
                            street = entity.direccion.street,
                            addressDetail = entity.direccion.addressDetail,
                            city = entity.direccion.city,
                            comuna = entity.direccion.comuna
                        ),
                        items = emptyList(),
                        paymentMethod = entity.paymentMethod,
                        cardInfo = null,
                        status = entity.status,
                        createdAt = entity.createdAt,
                        costoEnvio = entity.costoEnvio
                    )
                }

                _historial.value = lista

            } catch (e: Exception) {
                _error.value = "Error al cargar historial: ${e.message}"
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
