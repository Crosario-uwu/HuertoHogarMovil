package com.example.huertohogarapp.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.huertohogarapp.ui.theme.VerdeEsmeralda

/**
 * Barra superior con el logo o nombre de la app.
 */
@Composable
fun HuertoTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Huerto Hogar",
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = VerdeEsmeralda,
            titleContentColor = androidx.compose.ui.graphics.Color.White
        )
    )
}

