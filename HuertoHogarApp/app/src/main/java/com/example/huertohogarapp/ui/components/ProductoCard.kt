package com.example.huertohogarapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.huertohogarapp.model.Categoria
import com.example.huertohogarapp.model.Producto
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme

/**
 * Tarjeta visual de producto con nombre, origen, precio y botón de agregar.
 */
@Composable
fun ProductoCard(
    producto: Producto,
    onClick: () -> Unit,
    onAgregarCarrito: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
            Text(producto.origen, style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(4.dp))
            Text(
                "${producto.precio} CLP",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            PrimaryButton(text = "Agregar al carrito", onClick = onAgregarCarrito)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductoCardPreview() {
    HuertoHogarTheme {
        ProductoCard(
            producto = Producto(
                id = "FR001",
                nombre = "Manzanas Fuji",
                categoria = Categoria.FRUTAS,
                precio = 1200,
                origen = "Valle del Maule"
            ),
            onClick = {},
            onAgregarCarrito = {}
        )
    }
}
