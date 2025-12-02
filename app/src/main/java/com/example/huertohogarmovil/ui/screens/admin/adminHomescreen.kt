package com.example.huertohogarmovil.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogarmovil.ui.navigation.AppScreen
import com.example.huertohogarmovil.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    navController: NavController,
    adminVM: AdminViewModel
) {

    val loading by adminVM.loading.collectAsState()
    val error by adminVM.error.collectAsState()
    val mensaje by adminVM.mensaje.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel Administrador") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Productos
            AdminButton(
                icon = Icons.Filled.Inventory,
                text = "Productos",
                onClick = {
                    adminVM.cargarProductos()
                    navController.navigate(AppScreen.AdminProductos.route)
                }
            )

            // Escáner
            AdminButton(
                icon = Icons.Filled.QrCodeScanner,
                text = "Escanear Producto",
                onClick = {
                    navController.navigate(AppScreen.AdminScan.route)
                }
            )

            // Usuarios
            AdminButton(
                icon = Icons.Filled.Group,
                text = "Usuarios",
                onClick = {
                    adminVM.cargarUsuarios()
                    navController.navigate(AppScreen.AdminUsuarios.route)
                }
            )

            // Órdenes
            AdminButton(
                icon = Icons.Filled.ShoppingCart,
                text = "Órdenes",
                onClick = {
                    adminVM.cargarOrdenes()
                    navController.navigate(AppScreen.AdminOrdenes.route)
                }
            )

            // Cerrar sesión
            AdminButton(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                text = "Cerrar sesión",
                onClick = {
                    adminVM.limpiarMensajes()
                    navController.navigate(AppScreen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )

            if (loading) CircularProgressIndicator()

            error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            mensaje?.let {
                Text(it, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun AdminButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = text)
            Spacer(Modifier.width(12.dp))
            Text(text)
        }
    }
}
