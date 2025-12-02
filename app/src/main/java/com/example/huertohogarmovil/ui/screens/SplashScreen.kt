package com.example.huertohogarmovil.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogarmovil.R
import com.example.huertohogarmovil.ui.components.LeafLoading
import com.example.huertohogarmovil.ui.navigation.AppScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    // Estados internos
    var mostrarLoading by remember { mutableStateOf(false) }

    // Animación logo
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    // INICIAR ANIMACIONES Y NAVEGAR
    LaunchedEffect(Unit) {

        // Escala logo
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            )
        )

        // Fade in
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(900)
        )

        delay(1000)

        mostrarLoading = true

        delay(1200)

        // Navegación → Login
        navController.navigate(AppScreen.Login.route) {
            popUpTo(0) { inclusive = true }
        }
    }

    // ==========================
    //        UI FINAL
    // ==========================

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background    // ← CORREGIDO
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // LOGO con animación
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(180.dp)
                        .scale(scale.value)
                        .alpha(alpha.value)
                )

                Spacer(Modifier.height(30.dp))

                // LOADING HOJA
                if (mostrarLoading) {
                    LeafLoading()
                }
            }
        }
    }
}
