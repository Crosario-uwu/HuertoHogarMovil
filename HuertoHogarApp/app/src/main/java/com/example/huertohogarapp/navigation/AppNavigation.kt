package com.example.huertohogarapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.huertohogarapp.screen.FormularioScreen
import com.example.huertohogarapp.screen.ResumenScreen
import com.example.huertohogarapp.viewmodel.FormularioViewModel

@Composable
fun AppNavigation() {
    // Controlador de navegación
    val navController = rememberNavController()

    // ViewModel que mantiene el estado del formulario
    val viewModel: FormularioViewModel = viewModel()

    // Gráfico de navegación
    NavHost(
        navController = navController,
        startDestination = "formulario"
    ) {
        composable("formulario") {
            // Pantalla principal del formulario
            FormularioScreen(
                onEnviar = { navController.navigate("resumen") },
                viewModel = viewModel
            )
        }

        composable("resumen") {
            // Obtenemos el estado actual del formulario desde el ViewModel
            val estado = viewModel.uiState.value
            ResumenScreen(estado)
        }
    }
}
