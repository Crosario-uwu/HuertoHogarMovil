package com.example.huertohogarmovil.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CampoTexto(
    valor: String,
    onValueChange: (String) -> Unit,
    etiqueta: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        label = { Text(etiqueta) },
        modifier = modifier
    )
}
