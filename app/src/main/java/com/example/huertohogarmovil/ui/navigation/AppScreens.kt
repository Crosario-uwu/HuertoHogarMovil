package com.example.huertohogarmovil.ui.navigation

sealed class AppScreen(val route: String) {

    // AUTH
    object Login : AppScreen("login")
    object Register : AppScreen("register")

    // USER
    object Home : AppScreen("home")
    object Splash : AppScreen("splash")

    // ADMIN HOME
    object HomeAdmin : AppScreen("homeAdmin")

    object ProductoDetalle : AppScreen("producto_detalle/{code}") {
        fun createRoute(code: String) = "producto_detalle/$code"
    }

    object Carrito : AppScreen("carrito")
    object Checkout : AppScreen("checkout")
    object Perfil : AppScreen("perfil")
    object Historial : AppScreen("historial")
    object EditarPerfil : AppScreen("editar_perfil")

    // ADMIN
    object AdminUsuarios : AppScreen("admin_usuarios")
    object AdminProductos : AppScreen("admin_productos")
    object AdminOrdenes : AppScreen("admin_ordenes")

    object AdminDetalleOrden : AppScreen("admin_detalle_orden/{id}") {
        fun createRoute(id: Long) = "admin_detalle_orden/$id"
    }

    object AdminScan : AppScreen("admin_scan")
}
