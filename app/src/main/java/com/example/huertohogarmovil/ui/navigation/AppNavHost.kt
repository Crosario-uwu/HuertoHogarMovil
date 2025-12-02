package com.example.huertohogarmovil.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.huertohogarmovil.ui.navigation.AppScreen.*
import com.example.huertohogarmovil.viewmodel.*
import com.example.huertohogarmovil.ui.screens.*
import com.example.huertohogarmovil.ui.screens.admin.*

@Composable
fun AppNavHost(
    navController: NavHostController,
    authVM: AuthViewModel,
    productoVM: ProductoViewModel,
    carritoVM: CarritoViewModel,
    ordenVM: OrdenViewModel,
    perfilVM: PerfilViewModel,
    adminVM: AdminViewModel
) {

    NavHost(
        navController = navController,
        startDestination = Splash.route
    ) {

        // ------------------ SPLASH ------------------
        composable(Splash.route) {
            SplashScreen(navController)
        }

        // ------------------ AUTH ------------------
        composable(Login.route) {
            LoginScreen(navController, authVM)
        }

        composable(Register.route) {
            RegisterScreen(navController, authVM)
        }

        // ------------------ HOME USUARIO ------------------
        composable(Home.route) {
            HomeScreen(navController, productoVM)
        }

        // ------------------ DETALLE DE PRODUCTO (USER + ADMIN)
        composable(ProductoDetalle.route) { backStack ->
            val code = backStack.arguments?.getString("code") ?: ""

            ProductoDetalleScreen(
                navController = navController,
                code = code,
                productoVM = productoVM,
                carritoVM = carritoVM
            )
        }

        composable(Carrito.route) {
            CarritoScreen(navController, carritoVM)
        }

        composable(Checkout.route) {
            CheckoutScreen(navController, carritoVM, ordenVM)
        }

        composable(Perfil.route) {
            PerfilScreen(navController, perfilVM, authVM)
        }

        composable(EditarPerfil.route) {
            EditarPerfilScreen(perfilVM, authVM)
        }

        // ------------------ ADMIN AREA ------------------

        // HOME ADMIN
        composable(HomeAdmin.route) {
            AdminHomeScreen(navController, adminVM)
        }

        // LISTA DE USUARIOS (ADMIN)
        composable(AdminUsuarios.route) {
            AdminUsuariosScreen(navController, adminVM)
        }

        // LISTA / GESTIÓN DE PRODUCTOS (ADMIN)
        composable(AdminProductos.route) {
            AdminGestionProductosScreen(navController, productoVM)
        }


        // ESCÁNER DE PRODUCTO (ADMIN)
        composable(AdminScan.route) {
            AdminScanProductoScreen(navController, productoVM)
        }





    }

}
