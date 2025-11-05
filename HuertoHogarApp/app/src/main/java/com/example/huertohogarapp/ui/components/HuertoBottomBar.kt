package com.example.huertohogarapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Barra de navegación inferior con tres destinos principales.
 */
@Composable
fun HuertoBottomBar(
    onHome: () -> Unit,
    onCatalogo: () -> Unit,
    onPerfil: () -> Unit
) {
    val (seleccionado, setSeleccionado) = remember { mutableStateOf(0) }

    NavigationBar {
        NavigationBarItem(
            selected = seleccionado == 0,
            onClick = { setSeleccionado(0); onHome() },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = seleccionado == 1,
            onClick = { setSeleccionado(1); onCatalogo() },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Catálogo") },
            label = { Text("Catálogo") }
        )
        NavigationBarItem(
            selected = seleccionado == 2,
            onClick = { setSeleccionado(2); onPerfil() },
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}
