package com.example.huertohogarmovil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogarmovil.data.local.entity.CarritoEntity
import com.example.huertohogarmovil.data.local.entity.CarritoItemCard
import com.example.huertohogarmovil.ui.navigation.AppScreen
import com.example.huertohogarmovil.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    carritoVM: CarritoViewModel
) {
    val carrito by carritoVM.items.collectAsState()
    val total = carritoVM.total()   // porque es una función, NO un StateFlow

    Scaffold(
        topBar = { TopAppBar(title = { Text("Mi Carrito") }) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(15.dp)
                .fillMaxSize()
        ) {

            // Carrito vacío
            if (carrito.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Tu carrito está vacío")
                }
                return@Column
            }

            // LISTA DE PRODUCTOS EN EL CARRITO
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(carrito) { item: CarritoEntity ->
                    CarritoItemCard(item)
                }
            }

            Spacer(Modifier.height(10.dp))

            // TOTAL
            Text(
                text = "Subtotal: $${total}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Envío: $3990",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Total final: $${total + 3990}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(20.dp))

            // BOTÓN CHECKOUT
            Button(
                onClick = { navController.navigate(AppScreen.Checkout.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Ir al Checkout")
            }

            Spacer(Modifier.height(8.dp))

            // BOTÓN VACIAR CARRITO
            OutlinedButton(
                onClick = { carritoVM.limpiar() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Red
                )
            ) {
                Text("Vaciar Carrito")
            }
        }
    }
}
