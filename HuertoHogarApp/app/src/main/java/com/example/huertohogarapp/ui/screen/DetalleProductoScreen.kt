package com.example.huertohogarapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.huertohogarapp.model.Categoria
import com.example.huertohogarapp.model.Producto
import com.example.huertohogarapp.ui.components.RatingBar
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme

@Composable
fun DetalleProductoScreen(
    producto: Producto,
    promedioRating: Double,
    onAgregarCarrito: () -> Unit,
    onVerResenas: () -> Unit,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(producto.nombre, style = MaterialTheme.typography.titleLarge)
        Text(producto.origen, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(8.dp))
        Text(producto.descripcion, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        Text("Precio: ${producto.precio} CLP", style = MaterialTheme.typography.titleMedium)
        Text("Stock: ${producto.stock}", style = MaterialTheme.typography.bodySmall)

        Spacer(Modifier.height(12.dp))
        Divider()
        Spacer(Modifier.height(8.dp))

        Text("Calificación promedio: ${"%.1f".format(promedioRating)} ⭐")
        Spacer(Modifier.height(4.dp))
        RatingBar(
            rating = promedioRating.toInt().coerceIn(0, 5),
            onRatingChange = {}
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = onAgregarCarrito, modifier = Modifier.fillMaxWidth()) {
            Text("Agregar al carrito")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onVerResenas, modifier = Modifier.fillMaxWidth()) {
            Text("Ver reseñas")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleProductoScreenPreview() {
    HuertoHogarTheme {
        DetalleProductoScreen(
            producto = Producto(
                id = "FR001",
                nombre = "Manzanas Fuji",
                descripcion = "Manzanas crujientes y dulces.",
                precio = 1200,
                categoria = Categoria.FRUTAS,
                origen = "Valle del Maule",
                stock = 150
            ),
            promedioRating = 4.5,
            onAgregarCarrito = {},
            onVerResenas = {},
            onVolver = {}
        )
    }
}
