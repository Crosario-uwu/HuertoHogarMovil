package com.example.huertohogarmovil

import android.app.Application
import androidx.room.Room
import com.example.huertohogarmovil.data.local.HuertoDatabase
import com.example.huertohogarmovil.data.repository.ProductoRepository
import com.example.huertohogarmovil.data.repository.UserRepository

class HuertoHogarApp : Application() {

    // Contenedor "manual" de dependencias
    lateinit var productoRepository: ProductoRepository
        private set

    lateinit var userRepository: UserRepository
        private set

    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(
            applicationContext,
            HuertoDatabase::class.java,
            "huerto_hogar_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        val productDao = db.productoDao()
        productoRepository = ProductoRepository(productDao)

        userRepository = UserRepository(db.userDao())
    }
}
