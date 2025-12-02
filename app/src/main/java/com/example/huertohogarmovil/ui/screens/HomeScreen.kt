package com.example.huertohogarmovil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.huertohogarmovil.model.Producto
import com.example.huertohogarmovil.ui.navigation.AppScreen
import com.example.huertohogarmovil.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    productoVM: ProductoViewModel
) {
    val productos by productoVM.productos.collectAsState()
    val loading by productoVM.loading.collectAsState()

    // Estado del buscador
    var search by remember { mutableStateOf("") }

    // Categorías dinámicas
    val categorias = productos.map { it.category }.distinct()

    var categoriaSeleccionada by remember { mutableStateOf("Todos") }

    // Cargar productos al entrar
    LaunchedEffect(Unit) { productoVM.cargarProductos() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Huerto Hogar") },
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(10.dp)
                .fillMaxSize()
        ) {

            // 🔎 BUSCADOR
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Buscar producto...") }
            )

            Spacer(Modifier.height(10.dp))

            // 🏷 CATEGORÍAS
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {

                item {
                    CategoriaChip(
                        texto = "Todos",
                        seleccionado = categoriaSeleccionada == "Todos",
                        onClick = { categoriaSeleccionada = "Todos" }
                    )
                }

                items(categorias) { cat ->
                    CategoriaChip(
                        texto = cat,
                        seleccionado = categoriaSeleccionada == cat,
                        onClick = { categoriaSeleccionada = cat }
                    )
                }
            }

            Spacer(Modifier.height(15.dp))

            // 🔄 LOADING
            if (loading) {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                return@Column
            }

            // FILTRO POR CATEGORÍA Y BÚSQUEDA
            val listaFiltrada = productos.filter { p ->
                (categoriaSeleccionada == "Todos" || p.category == categoriaSeleccionada) &&
                        p.title.lowercase().contains(search.lowercase())
            }

            // 🛒 LISTA DE PRODUCTOS
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listaFiltrada) { productos ->
                    ProductoCard(productos) {
                        navController.navigate(
                            AppScreen.ProductoDetalle.createRoute(productos.id.toString())
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriaChip(texto: String, seleccionado: Boolean, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
        label = { Text(texto) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = if (seleccionado) MaterialTheme.colorScheme.primary else Color.LightGray,
            labelColor = if (seleccionado) Color.White else Color.Black
        )
    )
}

@Composable
fun ProductoCard(producto: Producto, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {

        Row(modifier = Modifier.padding(12.dp)) {

            // Imagen
            Image(
                painter = rememberAsyncImagePainter(
                    "https://dummyjson.com/image/i/products/${producto.thumbnail}.jpg"
                ),
                contentDescription = producto.title,
                modifier = Modifier
                    .size(85.dp)
            )

            Spacer(Modifier.width(15.dp))

            Column {
                Text(producto.title, fontWeight = FontWeight.Bold)
                Text("${producto.price} CLP", color = MaterialTheme.colorScheme.primary)
                Text("Stock: ${producto.stock}", color = Color.Gray)
            }
        }
    }
}
