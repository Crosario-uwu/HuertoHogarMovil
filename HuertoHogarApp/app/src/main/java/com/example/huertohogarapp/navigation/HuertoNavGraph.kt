package com.example.huertohogarapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.huertohogarapp.R
import com.example.huertohogarapp.model.Producto
import com.example.huertohogarapp.ui.screen.*
import com.example.huertohogarapp.viewmodel.*

/**
 * Rutas de la app Huerto Hogar.
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Inicio : Screen("inicio")
    object Catalogo : Screen("catalogo")
    object Carrito : Screen("carrito")
    object Checkout : Screen("checkout")
    object Perfil : Screen("perfil")
    object Formulario : Screen("formulario")
    object SelectorImagen : Screen("selector_imagen")
    object Pedidos : Screen("pedidos")
    object MapaTiendas : Screen("mapa_tiendas")

    // Rutas con argumentos
    object DetalleProducto : Screen("detalle_producto/{productoId}") {
        fun createRoute(productoId: String) = "detalle_producto/$productoId"
    }

    object Resenas : Screen("resenas/{productoId}") {
        fun createRoute(productoId: String) = "resenas/$productoId"
    }
}

/**
 * Gráfico de navegación principal.
 */
@Composable
fun HuertoNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        // SPLASH
        composable(Screen.Splash.route) {
            SplashScreen(
                logoResId = R.drawable.ic_launcher_foreground,
                onAnimationEnd = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // LOGIN
        composable(Screen.Login.route) {
            val authVm: AuthViewModel = viewModel()
            LoginScreen(
                authViewModel = authVm,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Inicio.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // REGISTER
        composable(Screen.Register.route) {
            val authVm: AuthViewModel = viewModel()
            RegisterScreen(
                authViewModel = authVm,
                onNavigateToLogin = { navController.popBackStack() },
                onNavigateToHome = {
                    navController.navigate(Screen.Inicio.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        // INICIO
        composable(Screen.Inicio.route) {
            InicioScreen(
                onIrCatalogo = { navController.navigate(Screen.Catalogo.route) },
                onIrPerfil = { navController.navigate(Screen.Perfil.route) },
                onIrPedidos = { navController.navigate(Screen.Pedidos.route) }
            )
        }

        // CATÁLOGO
        composable(Screen.Catalogo.route) {
            val catalogoVm: CatalogoViewModel = viewModel()
            val carritoVm: CarritoViewModel = viewModel()
            CatalogoScreen(
                catalogoViewModel = catalogoVm,
                carritoViewModel = carritoVm,
                onVerDetalle = { producto: Producto ->
                    navController.navigate(Screen.DetalleProducto.createRoute(producto.id))
                }
            )
        }

        // DETALLE PRODUCTO
        composable(
            route = Screen.DetalleProducto.route,
            arguments = listOf(
                navArgument("productoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("productoId") ?: return@composable

            val productVm: ProductViewModel = viewModel()
            val carritoVm: CarritoViewModel = viewModel()
            val resenaVm: ResenaViewModel = viewModel()

            val producto = productVm.getProductoPorId(productoId) ?: return@composable
            val promedio = resenaVm.promedioProducto(productoId)

            DetalleProductoScreen(
                producto = producto,
                promedioRating = promedio,
                onAgregarCarrito = { carritoVm.agregar(producto) },
                onVerResenas = {
                    navController.navigate(Screen.Resenas.createRoute(productoId))
                },
                onVolver = { navController.popBackStack() }
            )
        }

        // CARRITO
        composable(Screen.Carrito.route) {
            val carritoVm: CarritoViewModel = viewModel()
            CarritoScreen(
                carritoViewModel = carritoVm,
                onIrCheckout = { navController.navigate(Screen.Checkout.route) }
            )
        }

        // CHECKOUT
        composable(Screen.Checkout.route) {
            val pedidoVm: PedidoViewModel = viewModel()
            CheckoutScreen(
                pedidoViewModel = pedidoVm,
                onPedidoConfirmado = {
                    // después de confirmar, ir a inicio
                    navController.navigate(Screen.Inicio.route) {
                        popUpTo(Screen.Carrito.route) { inclusive = true }
                    }
                }
            )
        }

        // PERFIL
        composable(Screen.Perfil.route) {
            val usuarioVm: UsuarioViewModel = viewModel()
            PerfilScreen(
                usuarioViewModel = usuarioVm,
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Inicio.route) { inclusive = true }
                    }
                }
            )
        }

        // FORMULARIO DE PREFERENCIAS
        composable(Screen.Formulario.route) {
            val formularioVm: FormularioViewModel = viewModel()
            FormularioScreen(viewModel = formularioVm)
        }

        // SELECTOR DE IMAGEN
        composable(Screen.SelectorImagen.route) {
            val selectorVm: SelectorImagenViewModel = viewModel()
            PantallaSelectorImagen(viewModel = selectorVm)
        }

        // PEDIDOS
        composable(Screen.Pedidos.route) {
            val pedidoVm: PedidoViewModel = viewModel()
            val usuarioVm: UsuarioViewModel = viewModel()
            val usuario = usuarioVm.getUsuarioActual()
            val lista = if (usuario == null) emptyList()
            else pedidoVm.pedidosUsuarioActual.value

            PedidosScreen(pedidos = lista)
        }

        // RESEÑAS
        composable(
            route = Screen.Resenas.route,
            arguments = listOf(
                navArgument("productoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("productoId") ?: return@composable
            val resenaVm: ResenaViewModel = viewModel()
            val usuarioVm: UsuarioViewModel = viewModel()
            val nombreUsuario = usuarioVm.getUsuarioActual()?.nombre ?: "Invitado"

            ResenasScreen(
                productoId = productoId,
                usuarioNombre = nombreUsuario,
                resenaViewModel = resenaVm
            )
        }

        // MAPA DE TIENDAS
        composable(Screen.MapaTiendas.route) {
            val mapaVm: MapaTiendasViewModel = viewModel()
            MapaTiendasScreen(sucursales = mapaVm.puntosTiendas.value)
        }
    }
}
