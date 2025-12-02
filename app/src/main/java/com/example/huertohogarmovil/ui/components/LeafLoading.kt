package com.example.huertohogarmovil.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.huertohogarmovil.R
import kotlinx.coroutines.delay

@Composable
fun LeafLoading() {

    // Estados independientes por hoja
    var offset1 by remember { mutableStateOf(0.dp) }
    var offset2 by remember { mutableStateOf(0.dp) }
    var offset3 by remember { mutableStateOf(0.dp) }

    // Animaciones suaves por hoja
    val anim1 by animateDpAsState(
        targetValue = offset1,
        animationSpec = tween(400),
        label = "leaf1"
    )
    val anim2 by animateDpAsState(
        targetValue = offset2,
        animationSpec = tween(400),
        label = "leaf2"
    )
    val anim3 by animateDpAsState(
        targetValue = offset3,
        animationSpec = tween(400),
        label = "leaf3"
    )

    // Secuencia (una sube → baja → pasa a la siguiente)
    LaunchedEffect(Unit) {
        while (true) {

            // HOJA 1
            offset1 = (-20).dp
            delay(250)
            offset1 = 0.dp
            delay(150)

            // HOJA 2
            offset2 = (-20).dp
            delay(250)
            offset2 = 0.dp
            delay(150)

            // HOJA 3
            offset3 = (-20).dp
            delay(250)
            offset3 = 0.dp
            delay(150)
        }
    }

    // UI
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.hoja1),
            contentDescription = "Hoja 1",
            modifier = Modifier
                .padding(5.dp)
                .offset(y = anim1)
                .size(32.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.hoja2),
            contentDescription = "Hoja 2",
            modifier = Modifier
                .padding(5.dp)
                .offset(y = anim2)
                .size(32.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.hoja3),
            contentDescription = "Hoja 3",
            modifier = Modifier
                .padding(5.dp)
                .offset(y = anim3)
                .size(32.dp)
        )
    }
}
