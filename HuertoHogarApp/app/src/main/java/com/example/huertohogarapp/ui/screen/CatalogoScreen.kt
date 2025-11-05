package com.example.huertohogarapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogarapp.model.Categoria
import com.example.huertohogarapp.model.Producto
import com.example.huertohogarapp.ui.components.CategoriaChip
import com.example.huertohogarapp.ui.components.EmptyState
import com.example.huertohogarapp.ui.components.ProductoCard
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme
import com.example.huertohogarapp.viewmodel.CarritoViewModel
import com.example.huertohogarapp.viewmodel.CatalogoViewModel

@Composable
fun CatalogoScreen(
    onVerDetalle: (Producto) -> Unit,
    catalogoViewModel: CatalogoViewModel = viewModel(),
    carritoViewModel: CarritoViewModel = viewModel()
) {
    val productos by catalogoViewModel.productos.collectAsState()
    val categoriaSeleccionada by catalogoViewModel.categoriaSeleccionada.collectAsState()
    val busqueda by catalogoViewModel.busqueda.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Catálogo de productos")
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = busqueda,
            onValueChange = catalogoViewModel::actualizarBusqueda,
            label = { Text("Buscar producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row {
            Categoria.values().forEach { cat ->
                CategoriaChip(
                    categoria = cat,
                    seleccionado = categoriaSeleccionada == cat,
                    onClick = {
                        val nueva = if (categoriaSeleccionada == cat) null else cat
                        catalogoViewModel.seleccionarCategoria(nueva)
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        if (productos.isEmpty()) {
            EmptyState(
                mensaje = "No hay productos que coincidan con tu búsqueda.",
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            LazyColumn {
                items(productos) { producto ->
                    ProductoCard(
                        producto = producto,
                        onClick = { onVerDetalle(producto) },
                        onAgregarCarrito = { carritoViewModel.agregar(producto) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogoScreenPreview() {
    HuertoHogarTheme {
        CatalogoScreen(onVerDetalle = {})
    }
}
