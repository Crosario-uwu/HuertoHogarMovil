package com.example.huertohogarmovil.data.local.entity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun CarritoItemCard(item: CarritoEntity) {

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            Modifier
                .padding(12.dp)
                .height(IntrinsicSize.Min)
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    "https://dummyjson.com/image/i/products/${item.imageName}.jpg"
                ),
                contentDescription = item.name,
                modifier = Modifier.size(80.dp)
            )

            Spacer(Modifier.width(15.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(item.name, style = MaterialTheme.typography.titleMedium)
                Text("Precio: ${item.price} CLP")
                Text("Cantidad: ${item.quantity}")

                Spacer(Modifier.height(4.dp))

                Text(
                    "Subtotal: ${item.price * item.quantity} CLP",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
