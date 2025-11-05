package com.example.huertohogarapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.huertohogarapp.model.EstadoPedido
import com.example.huertohogarapp.model.ItemCarrito
import com.example.huertohogarapp.model.Pedido
import com.example.huertohogarapp.model.Producto
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme

@Composable
fun PedidosScreen(
    pedidos: List<Pedido>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Mis pedidos", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        if (pedidos.isEmpty()) {
            Text("Aún no has realizado pedidos.")
        } else {
            LazyColumn {
                items(pedidos) { pedido ->
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text("Pedido #${pedido.id}")
                        Text("Estado: ${pedido.estado}")
                        Text("Total: ${pedido.total} CLP")
                        Text("Items: ${pedido.items.size}")
                    }
                    Divider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PedidosScreenPreview() {
    val fakePedido = Pedido(
        id = "PED001",
        usuarioId = "USR01",
        items = listOf(
            ItemCarrito(
                producto = Producto(id = "FR001", nombre = "Manzanas", precio = 1000),
                cantidad = 2
            )
        ),
        total = 2000,
        estado = EstadoPedido.PREPARANDO
    )

    HuertoHogarTheme {
        PedidosScreen(pedidos = listOf(fakePedido))
    }
}
