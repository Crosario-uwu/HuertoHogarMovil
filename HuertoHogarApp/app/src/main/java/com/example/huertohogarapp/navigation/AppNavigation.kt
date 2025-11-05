package com.example.huertohogarapp.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.huertohogarapp.ui.components.HuertoBottomBar
import com.example.huertohogarapp.ui.components.HuertoTopBar

/**
 * Punto de entrada de navegación de la app.
 * Crea el NavController, muestra TopBar y BottomBar,
 * y delega las pantallas a HuertoNavGraph.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { HuertoTopBar() },
        bottomBar = {
            HuertoBottomBar(
                onHome = {
                    navController.navigate(Screen.Inicio.route) {
                        popUpTo(Screen.Inicio.route) { inclusive = false }
                    }
                },
                onCatalogo = {
                    navController.navigate(Screen.Catalogo.route) {
                        popUpTo(Screen.Inicio.route) { inclusive = false }
                    }
                },
                onPerfil = {
                    navController.navigate(Screen.Perfil.route) {
                        popUpTo(Screen.Inicio.route) { inclusive = false }
                    }
                }
            )
        }
    ) { innerPadding ->
        HuertoNavGraph(
            navController = navController,
            innerPadding = innerPadding
        )
    }
}
