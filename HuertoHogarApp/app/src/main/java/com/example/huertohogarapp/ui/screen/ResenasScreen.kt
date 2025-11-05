package com.example.huertohogarapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.huertohogarapp.model.Resena
import com.example.huertohogarapp.ui.components.RatingBar
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme
import com.example.huertohogarapp.viewmodel.ResenaViewModel

@Composable
fun ResenasScreen(
    productoId: String,
    usuarioNombre: String,
    resenaViewModel: ResenaViewModel
) {
    val resenas by resenaViewModel.resenas.collectAsState()
    val resenasDeProducto = resenas.filter { it.productoId == productoId }

    var comentario by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Reseñas del producto", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        if (resenasDeProducto.isEmpty()) {
            Text("Aún no hay reseñas. ¡Sé la primera persona en opinar!")
        } else {
            LazyColumn {
                items(resenasDeProducto) { r ->
                    Column(modifier = Modifier.padding(vertical = 6.dp)) {
                        Text("${r.usuarioNombre} dice:")
                        Text(r.comentario)
                        Text("⭐".repeat(r.rating))
                    }
                    Divider()
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Escribe tu reseña", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        RatingBar(
            rating = rating,
            onRatingChange = { rating = it }
        )
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = comentario,
            onValueChange = { comentario = it },
            label = { Text("Comentario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                if (rating in 1..5 && comentario.isNotBlank()) {
                    resenaViewModel.agregarResena(
                        productoId = productoId,
                        usuarioNombre = usuarioNombre,
                        comentario = comentario,
                        rating = rating
                    )
                    comentario = ""
                    rating = 0
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Publicar reseña")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResenasScreenPreview() {
    val fakeVm = ResenaViewModel()
    HuertoHogarTheme {
        ResenasScreen(
            productoId = "FR001",
            usuarioNombre = "Karla",
            resenaViewModel = fakeVm
        )
    }
}
