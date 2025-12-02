package com.example.huertohogarmovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.huertohogarmovil.data.local.HuertoDatabase
import com.example.huertohogarmovil.data.repository.*
import com.example.huertohogarmovil.ui.navigation.AppNavHost
import com.example.huertohogarmovil.ui.theme.HuertoHogarTheme
import com.example.huertohogarmovil.viewmodel.*
import com.example.huertohogarmovil.viewmodel.factory.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // =====================
        // BASE DE DATOS ROOM
        // =====================
        val db = Room.databaseBuilder(
            applicationContext,
            HuertoDatabase::class.java,
            "huerto_hogar.db"
        ).build()

        // =====================
        // REPOSITORIOS
        // =====================
        val userRepo = UserRepository(db.userDao())
        val productoRepo = ProductoRepository(db.productoDao())
        val carritoRepo = CarritoRepository(db.carritoDao())
        val ordenRepo = OrdenRepository(
            ordenDao = db.ordenDao(),
            ordenItemDao = db.ordenItemDao(),
            carritoDao = db.carritoDao()
        )

        // =====================
        // CARGAR PRODUCTOS LOCALES (UNA SOLA VEZ)
        // =====================
        lifecycleScope.launch {
            val cantidad = productoRepo.countProductos()
            if (cantidad == 0) {
                productoRepo.cargarProductosLocalesIniciales()
            }
        }

        // =====================
        // VIEWMODELS
        // =====================
        val authVM by viewModels<AuthViewModel> { AuthViewModelFactory(userRepo) }
        val productoVM by viewModels<ProductoViewModel> { ProductoViewModelFactory(productoRepo) }
        val carritoVM by viewModels<CarritoViewModel> { CarritoViewModelFactory(carritoRepo) }
        val ordenVM by viewModels<OrdenViewModel> { OrdenViewModelFactory(ordenRepo, carritoRepo) }
        val perfilVM by viewModels<PerfilViewModel> { PerfilViewModelFactory(userRepo, ordenRepo) }
        val adminVM by viewModels<AdminViewModel> { AdminViewModelFactory(userRepo, productoRepo, ordenRepo) }

        // =====================
        // UI
        // =====================
        setContent {
            HuertoHogarTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    authVM = authVM,
                    productoVM = productoVM,
                    carritoVM = carritoVM,
                    ordenVM = ordenVM,
                    perfilVM = perfilVM,
                    adminVM = adminVM
                )
            }
        }
    }


}
