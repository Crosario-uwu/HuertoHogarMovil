package com.example.huertohogarmovil.ui.navigation

sealed class AppScreen(val route: String) {
    object Splash : AppScreen("splash")
    object Login : AppScreen("login")
    object Register : AppScreen("register")
    object Home : AppScreen("home")
    object ProductoDetalle : AppScreen("productoDetalle/{code}")
    object Carrito : AppScreen("carrito")
    object Checkout : AppScreen("checkout")
    object Perfil : AppScreen("perfil")
    object EditarPerfil : AppScreen("editarPerfil")
    object HomeAdmin : AppScreen("adminHome")
    object AdminUsuarios : AppScreen("adminUsuarios")
    object AdminProductos : AppScreen("adminProductos")
    object AdminScan : AppScreen("adminScan")
}
