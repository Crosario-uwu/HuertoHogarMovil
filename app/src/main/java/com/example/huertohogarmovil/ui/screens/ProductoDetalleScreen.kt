package com.example.huertohogarmovil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import com.example.huertohogarmovil.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.huertohogarmovil.data.local.entity.CarritoEntity
import com.example.huertohogarmovil.viewmodel.CarritoViewModel
import com.example.huertohogarmovil.viewmodel.ProductoViewModel
import com.example.huertohogarmovil.ui.navigation.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoDetalleScreen(
    navController: NavController,
    code: String,
    productoVM: ProductoViewModel,
    carritoVM: CarritoViewModel
) {

    val producto by productoVM.productoDetalle.collectAsState()
    val loading by productoVM.loading.collectAsState()

    // 🔥 CARGAR PRODUCTO DESDE ROOM (no API)
    LaunchedEffect(code) {
        productoVM.cargarDetalleDesdeRoom(code.toInt())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(producto?.title ?: "Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(AppScreen.Carrito.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito"
                        )
                    }
                }
            )
        }
    ) { padding ->

        if (loading || producto == null) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        val item = producto!!
        var cantidad by remember { mutableIntStateOf(1) }

        Column(
            Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize()
        ) {

            // 🔥 Cargar imagen del drawable (Room guarda nombre del archivo)
            val context = LocalContext.current
            val imgRes = remember(item.thumbnail) {
                val res = context.resources.getIdentifier(item.thumbnail, "drawable", context.packageName)
                if (res != 0) res else R.drawable.ic_launcher_foreground
            }


            Image(
                painter = rememberAsyncImagePainter(imgRes),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(20.dp))

            Text(item.title, style = MaterialTheme.typography.titleLarge)
            Text(item.category, color = Color.Gray)

            Spacer(Modifier.height(10.dp))

            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF2E7D32)
            )

            Spacer(Modifier.height(10.dp))

            Text(item.description)

            Spacer(Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Cantidad:", Modifier.weight(1f))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = { if (cantidad > 1) cantidad-- }) { Text("-") }
                    Text("$cantidad")
                    Button(onClick = { cantidad++ }) { Text("+") }
                }
            }

            Spacer(Modifier.height(25.dp))

            // AGREGAR AL CARRITO
            Button(
                onClick = {
                    val entity = CarritoEntity(
                        id = 0,
                        productCode = item.id.toString(),
                        name = item.title,
                        price = item.price,
                        quantity = cantidad,
                        imageName = item.thumbnail.toString()
                    )

                    carritoVM.agregarProducto(entity, userId = 1)

                    navController.navigate(AppScreen.Carrito.route)
                },

                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Agregar al carrito")
            }
        }
    }
}
