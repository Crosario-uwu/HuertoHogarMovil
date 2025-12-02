package com.example.huertohogarmovil.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.huertohogarmovil.data.repository.OrdenRepository
import com.example.huertohogarmovil.data.repository.UserRepository
import com.example.huertohogarmovil.viewmodel.PerfilViewModel

class PerfilViewModelFactory(
    private val userRepository: UserRepository,
    private val ordenRepository: OrdenRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
            return PerfilViewModel(userRepository, ordenRepository) as T
        }
        throw IllegalArgumentException("ViewModel no reconocido")
    }
}
