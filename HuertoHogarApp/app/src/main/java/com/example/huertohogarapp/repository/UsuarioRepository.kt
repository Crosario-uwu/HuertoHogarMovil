package com.example.huertohogarapp.repository

import com.example.huertohogarapp.model.DireccionEntrega
import com.example.huertohogarapp.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Maneja registro, login y perfil del usuario.
 * Por ahora está en memoria. Luego se puede conectar a FirebaseAuth + Firestore.
 */
class UsuarioRepository {
    // "Base de datos" simple en memoria
    private val usuarios = mutableListOf<Usuario>()

    private var usuarioActual: Usuario? = null

    private val _usuarioFlow = MutableStateFlow<Usuario?>(null)

    val usuarioFlow: StateFlow<Usuario?> = _usuarioFlow

    fun registrar(nombre: String, correo: String, password: String, telefono: String): Result<Usuario> {
        if (usuarios.any { it.correo == correo }) {
            return Result.failure(Exception("El correo ya está registrado"))
        }

        val nuevo = Usuario(
            id = System.currentTimeMillis().toString(),
            nombre = nombre,
            correo = correo,
            telefono = telefono
        )
        usuarios.add(nuevo)
        usuarioActual = nuevo
        _usuarioFlow.value = nuevo
        return Result.success(nuevo)
    }

    fun login(correo: String, password: String): Result<Usuario> {
        val encontrado = usuarios.find { it.correo == correo }
        return if (encontrado != null) {
            usuarioActual = encontrado
            _usuarioFlow.value = encontrado
            Result.success(encontrado)
        } else {
            Result.failure(Exception("Credenciales inválidas"))
        }
    }

    fun logout() {
        usuarioActual = null
        _usuarioFlow.value = null
    }

    fun getUsuarioActual(): Usuario? = usuarioActual

    fun actualizarDireccion(direccion: DireccionEntrega) {
        usuarioActual = usuarioActual?.copy(direccion = direccion)
        actualizarUsuarioInterno(usuarioActual)
    }

    fun actualizarFotoPerfil(uri: String?) {
        usuarioActual = usuarioActual?.copy(fotoPerfilUri = uri)
        actualizarUsuarioInterno(usuarioActual)
    }

    private fun actualizarUsuarioInterno(usuario: Usuario?) {
        usuario ?: return
        val index = usuarios.indexOfFirst { it.id == usuario.id }
        if (index != -1) {
            usuarios[index] = usuario
        }
        _usuarioFlow.value = usuario
    }

    // TODO Firebase:
    // - Reemplazar lista en memoria por colección "usuarios" en Firestore
    // - Conectar registrar/login con FirebaseAuth
}
