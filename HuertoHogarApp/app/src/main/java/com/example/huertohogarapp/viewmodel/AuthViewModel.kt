package com.example.huertohogarapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.model.Usuario
import com.example.huertohogarapp.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()

    data class Error(val message: String) : AuthState()
}

class AuthViewModel(
    private val usuarioRepository: UsuarioRepository = UsuarioRepository()
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _currentUser = MutableStateFlow<Usuario?>(null)
    val currentUser: StateFlow<Usuario?> = _currentUser

    init {
        // Si más adelante cargas sesión desde almacenamiento local, lo haces aquí
        _currentUser.value = usuarioRepository.getUsuarioActual()
    }

    fun registrar(nombre: String, correo: String, password: String, telefono: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = usuarioRepository.registrar(nombre, correo, password, telefono)
            _authState.value = if (result.isSuccess) {
                _currentUser.value = result.getOrNull()
                AuthState.Success
            } else {
                AuthState.Error(result.exceptionOrNull()?.message ?: "Error al registrar")
            }
        }
    }

    fun login(correo: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = usuarioRepository.login(correo, password)
            _authState.value = if (result.isSuccess) {
                _currentUser.value = result.getOrNull()
                AuthState.Success
            } else {
                AuthState.Error(result.exceptionOrNull()?.message ?: "Error al iniciar sesión")
            }
        }
    }

    fun logout() {
        usuarioRepository.logout()
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
}
