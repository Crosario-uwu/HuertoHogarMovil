package com.example.huertohogarapp.ui.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.huertohogarapp.model.Categoria

/**
 * Chip de filtro de categoría (Frutas, Verduras, etc.).
 */
@Composable
fun CategoriaChip(
    categoria: Categoria,
    seleccionado: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = seleccionado,
        onClick = onClick,
        label = { Text(categoria.displayName) },
        modifier = modifier
    )
}
