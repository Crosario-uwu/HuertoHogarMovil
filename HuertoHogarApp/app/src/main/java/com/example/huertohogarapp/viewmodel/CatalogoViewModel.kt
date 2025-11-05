package com.example.huertohogarapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.model.Categoria
import com.example.huertohogarapp.model.Producto
import com.example.huertohogarapp.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
class CatalogoViewModel(
    private val productoRepository: ProductoRepository = ProductoRepository()
) : ViewModel() {

    private val _categoriaSeleccionada = MutableStateFlow<Categoria?>(null)
    val categoriaSeleccionada: StateFlow<Categoria?> = _categoriaSeleccionada

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda

    private val _productosFiltrados = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productosFiltrados

    init {
        viewModelScope.launch {
            combine(
                productoRepository.productos,
                _categoriaSeleccionada,
                _busqueda
            ) { lista, categoria, texto ->
                lista
                    .filter { categoria == null || it.categoria == categoria }
                    .filter {
                        texto.isBlank() ||
                                it.nombre.contains(texto, ignoreCase = true) ||
                                it.descripcion.contains(texto, ignoreCase = true)
                    }
            }.collect { filtrados ->
                _productosFiltrados.value = filtrados
            }
        }
    }

    private val db = Firebase.firestore
    fun seleccionarCategoria(categoria: Categoria?) {
        _categoriaSeleccionada.value = categoria
    }

    fun actualizarBusqueda(texto: String) {
        _busqueda.value = texto
    }

    fun obtenerProductoPorId(id: String): Producto? =
        productoRepository.getProductoPorId(id)
}
fun pruebaFirebase() {
    val data = hashMapOf("mensaje" to "Hola Huerto Hogar", "timestamp" to System.currentTimeMillis())
    db.collection("pruebas")
        .add(data)
}