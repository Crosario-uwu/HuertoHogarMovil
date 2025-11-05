package com.example.huertohogarapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.huertohogarapp.ui.components.PrimaryButton
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme

@Composable
fun InicioScreen(
    onIrCatalogo: () -> Unit,
    onIrPerfil: () -> Unit,
    onIrPedidos: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenida a Huerto Hogar 🌿", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text(
            "Compra frutas, verduras y productos orgánicos de productores locales.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(24.dp))
        PrimaryButton(text = "Ver catálogo", onClick = onIrCatalogo)
        Spacer(Modifier.height(8.dp))
        PrimaryButton(text = "Mi perfil", onClick = onIrPerfil)
        Spacer(Modifier.height(8.dp))
        PrimaryButton(text = "Mis pedidos", onClick = onIrPedidos)
    }
}

@Preview(showBackground = true)
@Composable
fun InicioScreenPreview() {
    HuertoHogarTheme {
        InicioScreen(
            onIrCatalogo = {},
            onIrPerfil = {},
            onIrPedidos = {}
        )
    }
}
