package com.example.huertohogarmovil.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.huertohogarmovil.data.repository.CarritoRepository
import com.example.huertohogarmovil.viewmodel.CarritoViewModel

class CarritoViewModelFactory(
    private val repository: CarritoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarritoViewModel::class.java)) {
            return CarritoViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel no reconocido")
    }
}
