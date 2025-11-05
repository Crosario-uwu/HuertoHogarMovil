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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogarapp.ui.components.EmptyState
import com.example.huertohogarapp.ui.components.PrimaryButton
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme
import com.example.huertohogarapp.viewmodel.CarritoViewModel

@Composable
fun CarritoScreen(
    onIrCheckout: () -> Unit,
    carritoViewModel: CarritoViewModel = viewModel()
) {
    val items by carritoViewModel.items.collectAsState()
    val total = carritoViewModel.total()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Tu carrito", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        if (items.isEmpty()) {
            EmptyState(
                mensaje = "Tu carrito está vacío. Agrega productos desde el catálogo.",
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            LazyColumn {
                items(items) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${item.producto.nombre} x${item.cantidad}")
                        Text("${item.subtotal} CLP")
                    }
                    Divider()
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("Total: $total CLP", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            PrimaryButton(
                text = "Confirmar pedido",
                onClick = onIrCheckout,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarritoScreenPreview() {
    HuertoHogarTheme {
        CarritoScreen(onIrCheckout = {})
    }
}
