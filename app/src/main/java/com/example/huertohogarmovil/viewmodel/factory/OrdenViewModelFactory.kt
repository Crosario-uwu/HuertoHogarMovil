package com.example.huertohogarmovil.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.huertohogarmovil.data.repository.CarritoRepository
import com.example.huertohogarmovil.data.repository.OrdenRepository
import com.example.huertohogarmovil.viewmodel.OrdenViewModel

class OrdenViewModelFactory(
    private val ordenRepository: OrdenRepository,
    private val carritoRepository: CarritoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrdenViewModel::class.java)) {
            return OrdenViewModel(ordenRepository, carritoRepository) as T
        }
        throw IllegalArgumentException("ViewModel no reconocido")
    }
}
