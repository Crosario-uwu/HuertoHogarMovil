package com.example.huertohogarmovil.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun CartIconAnimated(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    triggerAnimation: Boolean
) {
    val scope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }

    // ACTIVAR ANIMACIÓN CUANDO triggerAnimation CAMBIA
    LaunchedEffect(triggerAnimation) {
        if (triggerAnimation) {
            scope.launch {
                scale.animateTo(1.3f, tween(150))
                scale.animateTo(1f, tween(150))
            }
        }
    }

    Icon(
        imageVector = Icons.Default.ShoppingCart,
        contentDescription = "Carrito",
        modifier = modifier
            .size(28.dp)
            .clickable { onClick() }
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value
            )
    )
}
