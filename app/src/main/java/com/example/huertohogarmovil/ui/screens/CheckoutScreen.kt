package com.example.huertohogarmovil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogarmovil.model.*
import com.example.huertohogarmovil.ui.navigation.AppScreen
import com.example.huertohogarmovil.viewmodel.CarritoViewModel
import com.example.huertohogarmovil.viewmodel.OrdenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    carritoVM: CarritoViewModel,
    ordenVM: OrdenViewModel
) {
    val carrito by carritoVM.items.collectAsState()
    val total = carritoVM.total()   // porque es una función, NO un StateFlow

    val loading by ordenVM.loading.collectAsState()
    val error by ordenVM.error.collectAsState()
    val ordenCreadaId by ordenVM.ordenCreadaId.collectAsState()

    // --- ESTADO DEL FORMULARIO ---
    var calle by remember { mutableStateOf("") }
    var detalle by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var comuna by remember { mutableStateOf("") }

    // MÉTODO DE PAGO
    var metodo by remember { mutableStateOf(MetodoPago.EFECTIVO) }

    // CAMPOS TARJETA
    var numTarjeta by remember { mutableStateOf("") }
    var vencimiento by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var titular by remember { mutableStateOf("") }

    // NAVEGAR CUANDO SE CREA
    LaunchedEffect(ordenCreadaId) {
        if (ordenCreadaId != null) {
            navController.navigate(AppScreen.Historial.route) {
                popUpTo(AppScreen.Checkout.route) { inclusive = true }
            }
            ordenVM.limpiarEstadoOrden()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Checkout") })
        }
    ) { padding ->

        if (carrito.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                Alignment.Center
            ) {
                Text("Tu carrito está vacío")
            }
            return@Scaffold
        }

        Column(
            Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // DATOS DE ENVÍO
            Text("Datos de envío", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = calle,
                onValueChange = { calle = it },
                label = { Text("Calle y número") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = detalle,
                onValueChange = { detalle = it },
                label = { Text("Detalle (Depto, block)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = ciudad,
                onValueChange = { ciudad = it },
                label = { Text("Ciudad") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = comuna,
                onValueChange = { comuna = it },
                label = { Text("Comuna") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            // MÉTODO DE PAGO
            Text("Método de pago", style = MaterialTheme.typography.titleMedium)

            Row(verticalAlignment = Alignment.CenterVertically) {

                RadioButton(
                    selected = metodo == MetodoPago.EFECTIVO,
                    onClick = { metodo = MetodoPago.EFECTIVO }
                )
                Text("Efectivo", Modifier.padding(end = 25.dp))

                RadioButton(
                    selected = metodo == MetodoPago.TARJETA,
                    onClick = { metodo = MetodoPago.TARJETA }
                )
                Text("Tarjeta")
            }

            if (metodo == MetodoPago.TARJETA) {

                OutlinedTextField(
                    value = numTarjeta,
                    onValueChange = { numTarjeta = it },
                    label = { Text("Número de tarjeta") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = vencimiento,
                    onValueChange = { vencimiento = it },
                    label = { Text("Fecha (MM/YY)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    label = { Text("CVV") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = titular,
                    onValueChange = { titular = it },
                    label = { Text("Titular") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(25.dp))

            // RESUMEN
            Text("Resumen", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(10.dp))

            Text("Subtotal: $${total}")
            Text("Envío: $3990")

            Text(
                "Total a pagar: $${total + 3990}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(25.dp))

            Button(
                onClick = {

                    // ==============================
                    // VALIDAR CARRITO VACÍO
                    // ==============================
                    if (carrito.isEmpty()) {
                        ordenVM.setError("Tu carrito está vacío")
                        return@Button
                    }

                    // ==============================
                    // VALIDAR DATOS DE ENVÍO
                    // ==============================
                    if (calle.isBlank()) {
                        ordenVM.setError("Debe ingresar una calle")
                        return@Button
                    }
                    if (ciudad.isBlank()) {
                        ordenVM.setError("Debe ingresar una ciudad")
                        return@Button
                    }
                    if (comuna.isBlank()) {
                        ordenVM.setError("Debe ingresar una comuna")
                        return@Button
                    }

                    // ==============================
                    // VALIDAR TARJETA (si aplica)
                    // ==============================
                    if (metodo == MetodoPago.TARJETA) {

                        if (numTarjeta.length < 12) {
                            ordenVM.setError("Número de tarjeta inválido")
                            return@Button
                        }
                        if (!vencimiento.matches(Regex("^(0[1-9]|1[0-2])/\\d{2}\$"))) {
                            ordenVM.setError("Fecha vencimiento inválida (MM/YY)")
                            return@Button
                        }
                        if (cvv.length !in 3..4) {
                            ordenVM.setError("CVV inválido")
                            return@Button
                        }
                        if (titular.isBlank()) {
                            ordenVM.setError("Ingrese el nombre del titular")
                            return@Button
                        }
                    }

                    // Limpiar error previo
                    ordenVM.setError(null)

                    // ==============================
                    // CREAR OBJETOS
                    // ==============================
                    val envio = DatosEnvio(
                        street = calle,
                        addressDetail = detalle,
                        city = ciudad,
                        comuna = comuna
                    )

                    val card = if (metodo == MetodoPago.TARJETA)
                        CardInfo(numTarjeta, vencimiento, cvv, titular)
                    else null

                    // ==============================
                    // CREAR ORDEN
                    // ==============================
                    ordenVM.crearOrden(
                        userId = 1,  // cambiar cuando esté login real
                        datosEnvio = envio,
                        metodoPago = metodo,
                        tarjeta = card
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !loading
            ) {
                if (loading) CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(22.dp)
                )
                else Text("Confirmar compra")
            }



            // ERROR
            if (error != null) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}
