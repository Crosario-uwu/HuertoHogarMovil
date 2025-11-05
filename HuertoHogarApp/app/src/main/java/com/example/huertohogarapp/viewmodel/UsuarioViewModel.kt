package com.example.huertohogarapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.model.DireccionEntrega
import com.example.huertohogarapp.model.Usuario
import com.example.huertohogarapp.repository.UsuarioRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val usuarioRepository: UsuarioRepository = UsuarioRepository()
) : ViewModel() {

    val usuario: StateFlow<Usuario?> = usuarioRepository.usuarioFlow

  fun actualizarDireccion(direccion: DireccionEntrega) {
        viewModelScope.launch {
            usuarioRepository.actualizarDireccion(direccion)
        }
    }

    fun actualizarFotoPerfil(uri: String?) {
        viewModelScope.launch {
            usuarioRepository.actualizarFotoPerfil(uri)
        }
    }

    fun getUsuarioActual(): Usuario? = usuarioRepository.getUsuarioActual()
}
