package com.example.huertohogarmovil.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.huertohogarmovil.R

@Composable
fun ImagenInteligente(
    uri: String?,              // URI desde ViewModel
    modifier: Modifier = Modifier,
    size: Int = 130            // tamaño por defecto
) {

    val shape = CircleShape

    if (uri != null) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(uri)
                .crossfade(true)
                .build(),
            contentDescription = "Foto de perfil",
            modifier = modifier
                .size(size.dp)
                .clip(shape)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = shape
                )
        )

    } else {

        Image(
            painter = painterResource(id = R.drawable.logo), // usa el que quieras
            contentDescription = "Imagen por defecto",
            modifier = modifier
                .size(size.dp)
                .clip(shape)
                .background(Color.LightGray.copy(alpha = 0.25f))
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = shape
                )
        )
    }
}
