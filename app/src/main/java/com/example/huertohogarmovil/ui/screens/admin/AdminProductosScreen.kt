package com.example.huertohogarmovil.ui.screens.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.huertohogarmovil.model.Producto
import com.example.huertohogarmovil.ui.navigation.AppScreen
import com.example.huertohogarmovil.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminGestionProductosScreen(
    navController: NavController,
    productoVM: ProductoViewModel
) {
    val loading by productoVM.loading.collectAsState()
    val productos by productoVM.productos.collectAsState()
    val error by productoVM.error.collectAsState()

    // PARA FORMULARIO
    var showDialog by remember { mutableStateOf(false) }
    var editingProduct: Producto? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        productoVM.cargarProductos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Productos") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(AppScreen.HomeAdmin.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        editingProduct = null
                        showDialog = true
                    }) {
                        Icon(Icons.Default.Add, "Agregar producto")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .padding(15.dp)
                .fillMaxSize()
        ) {

            if (loading) {
                Box(Modifier.fillMaxSize(), Alignment.Center)
                { CircularProgressIndicator() }
                return@Column
            }

            error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            if (productos.isEmpty()) {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Text("No hay productos disponibles")
                }
                return@Column
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(productos) { producto ->
                    ProductoItemCard(
                        producto = producto,
                        onEdit = {
                            editingProduct = producto
                            showDialog = true
                        },
                        onDelete = {
                            productoVM.eliminarProducto(producto.id)
                        }
                    )
                }
            }
        }
    }

    if (showDialog) {
        ProductoFormDialog(
            productoVM = productoVM,
            producto = editingProduct,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun ProductoItemCard(producto: Producto, onEdit: () -> Unit, onDelete: () -> Unit) {


    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {



            Spacer(modifier = Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(producto.title, style = MaterialTheme.typography.titleMedium)
                Text("Precio: ${producto.price} CLP")
                Text("Categoría: ${producto.category}")
                Text("Stock: ${producto.stock}")
            }

            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormDialog(
    productoVM: ProductoViewModel,
    producto: Producto?,
    onDismiss: () -> Unit
) {

    var title by remember { mutableStateOf(producto?.title ?: "") }
    var price by remember { mutableStateOf(producto?.price?.toString() ?: "") }
    var category by remember { mutableStateOf(producto?.category ?: "") }
    var imageUrl by remember { mutableStateOf(producto?.thumbnail ?: "") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(if (producto == null) "Agregar Producto" else "Editar Producto") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Categoría") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("URL imagen o nombre archivo") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {

                if (title.isBlank() || price.isBlank()) return@Button

                if (producto == null) {
                    // Crear nuevo
                    productoVM.crearProducto(
                        title = title,
                        price = price.toInt(),
                        category = category,
                        description = "",
                    )
                } else {
                    // Actualizar existente
                    productoVM.actualizarProducto(
                        id = producto.id,
                        title = title,
                        price = price.toInt()
                    )
                }

                onDismiss()

            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
    @Composable
    fun AdminProductoCard(producto: Producto) {

        val context = LocalContext.current
        val imageRes = remember(producto.thumbnail) {
            context.resources.getIdentifier(
                producto.thumbnail,
                "drawable",
                context.packageName
            )
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {

            Row(Modifier.padding(12.dp)) {

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = producto.title,
                    modifier = Modifier.size(80.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.width(15.dp))

                Column(Modifier.weight(1f)) {
                    Text(producto.title, fontWeight = FontWeight.Bold)
                    Text("Precio: ${producto.price} CLP")
                    Text("Categoría: ${producto.category}")
                    Text("Stock: ${producto.stock}")
                }
            }
        }
    }

}
