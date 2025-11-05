package com.example.huertohogarapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogarapp.model.DireccionEntrega
import com.example.huertohogarapp.ui.components.FormTextField
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme
import com.example.huertohogarapp.viewmodel.PedidoViewModel

@Composable
fun CheckoutScreen(
    pedidoViewModel: PedidoViewModel = viewModel(),
    onPedidoConfirmado: () -> Unit
) {
    var calle by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var comuna by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Datos de envío", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        FormTextField(
            value = calle,
            onValueChange = { calle = it; error = null },
            label = "Calle"
        )
        Spacer(Modifier.height(8.dp))
        FormTextField(
            value = numero,
            onValueChange = { numero = it; error = null },
            label = "Número"
        )
        Spacer(Modifier.height(8.dp))
        FormTextField(
            value = comuna,
            onValueChange = { comuna = it; error = null },
            label = "Comuna"
        )
        Spacer(Modifier.height(8.dp))
        FormTextField(
            value = ciudad,
            onValueChange = { ciudad = it; error = null },
            label = "Ciudad"
        )
        Spacer(Modifier.height(8.dp))
        FormTextField(
            value = region,
            onValueChange = { region = it; error = null },
            label = "Región"
        )

        Spacer(Modifier.height(8.dp))

        if (error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (calle.isBlank() || numero.isBlank() || comuna.isBlank() || ciudad.isBlank()) {
                    error = "Completa todos los campos obligatorios"
                } else {
                    val dir = DireccionEntrega(
                        calle = calle,
                        numero = numero,
                        comuna = comuna,
                        ciudad = ciudad,
                        region = region
                    )
                    pedidoViewModel.confirmarPedido(dir)
                    onPedidoConfirmado()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar pedido")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    HuertoHogarTheme {
        CheckoutScreen(onPedidoConfirmado = {})
    }
}
