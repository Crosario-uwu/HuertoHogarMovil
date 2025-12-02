package com.example.huertohogarmovil.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.huertohogarmovil.data.repository.OrdenRepository
import com.example.huertohogarmovil.data.repository.ProductoRepository
import com.example.huertohogarmovil.data.repository.UserRepository
import com.example.huertohogarmovil.viewmodel.AdminViewModel

class AdminViewModelFactory(
    private val userRepository: UserRepository,
    private val productoRepository: ProductoRepository,
    private val ordenRepository: OrdenRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
            return AdminViewModel(userRepository, productoRepository, ordenRepository) as T
        }
        throw IllegalArgumentException("ViewModel no reconocido")
    }
}
