package com.example.huertohogarmovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarmovil.data.repository.UserRepository
import com.example.huertohogarmovil.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()


    // -----------------------------------------------------------
    // LIMPIAR ERROR
    // -----------------------------------------------------------
    fun setError(msg: String?) {
        _error.value = msg
    }


    // -----------------------------------------------------------
    // LOGIN
    // -----------------------------------------------------------
    fun login(email: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                val user = userRepository.login(email, password)
                if (user == null) {
                    _error.value = "Credenciales inválidas"
                    onResult(null)
                } else {
                    _error.value = null      // ← IMPORTANTE
                    onResult(user)
                }

            } catch (_: Exception) {
                _error.value = "Error al iniciar sesión"
            } finally {
                _loading.value = false
            }
        }
    }



    // -----------------------------------------------------------
    // REGISTRO (CORREGIDO)
    // -----------------------------------------------------------
    fun register(user: User, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                if (user.email.isBlank() || user.name.isBlank() || user.password.isBlank()) {
                    _error.value = "Debe completar los campos obligatorios"
                    onResult(false)
                    _loading.value = false
                    return@launch
                }

                // ✔ ESTE ES EL NOMBRE CORRECTO DE UserRepository
                userRepository.registrarUsuario(user)

                onResult(true)

            } catch (e: Exception) {
                _error.value = "No se pudo registrar: ${e.message}"
                onResult(false)
            } finally {
                _loading.value = false
            }
        }
    }


}
