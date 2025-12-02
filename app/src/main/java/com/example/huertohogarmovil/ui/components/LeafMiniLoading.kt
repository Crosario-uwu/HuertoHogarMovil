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
fun LeafMiniLoading() {

    var offset1 by remember { mutableStateOf(0.dp) }
    var offset2 by remember { mutableStateOf(0.dp) }
    var offset3 by remember { mutableStateOf(0.dp) }

    val anim1 by animateDpAsState(offset1, tween(350), label = "")
    val anim2 by animateDpAsState(offset2, tween(350), label = "")
    val anim3 by animateDpAsState(offset3, tween(350), label = "")

    LaunchedEffect(Unit) {
        while (true) {
            offset1 = (-10).dp
            delay(200)
            offset1 = 0.dp
            delay(150)

            offset2 = (-10).dp
            delay(200)
            offset2 = 0.dp
            delay(150)

            offset3 = (-10).dp
            delay(200)
            offset3 = 0.dp
            delay(150)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.hoja1),
            contentDescription = null,
            modifier = Modifier
                .size(18.dp)
                .offset(y = anim1)
                .padding(4.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.hoja2),
            contentDescription = null,
            modifier = Modifier
                .size(18.dp)
                .offset(y = anim2)
                .padding(4.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.hoja3),
            contentDescription = null,
            modifier = Modifier
                .size(18.dp)
                .offset(y = anim3)
                .padding(4.dp)
        )
    }
}
