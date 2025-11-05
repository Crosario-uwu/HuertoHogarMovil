package com.example.huertohogarapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.huertohogarapp.R
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    @DrawableRes logoResId: Int,
    onAnimationEnd: () -> Unit
) {
    val scale = remember { Animatable(0.3f) }
    val alpha = remember { Animatable(0f) }
    val offsetY = remember { Animatable(40f) } // 40dp hacia abajo
    val density = LocalDensity.current

    LaunchedEffect(Unit) {
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = keyframes {
                    durationMillis = 1200
                    0.3f at 0
                    1.1f at 400
                    0.95f at 700
                    1.02f at 900
                    1.0f at 1200
                }
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(800)
            )
        }
        launch {
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(800)
            )
        }

        delay(1500)
        onAnimationEnd()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha.value),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = logoResId),
            contentDescription = "Logo Huerto Hogar",
            modifier = Modifier.graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                translationY = with(density) { offsetY.value.dp.toPx() }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    HuertoHogarTheme {
        SplashScreen(
            logoResId = R.drawable.ic_launcher_foreground,
            onAnimationEnd = {}
        )
    }
}
