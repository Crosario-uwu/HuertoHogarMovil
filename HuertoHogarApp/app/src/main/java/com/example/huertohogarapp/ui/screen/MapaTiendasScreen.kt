package com.example.huertohogarapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.huertohogarapp.model.PuntoTienda
import com.example.huertohogarapp.ui.components.MapMarkerCard
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme

@Composable
fun MapaTiendasScreen(
    sucursales: List<PuntoTienda>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Sucursales Huerto Hogar", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        // Aquí podrías integrar Google Maps en EFT.
        Text(
            "Mapa próximamente (EFT). Por ahora, listado de sucursales:",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.height(8.dp))

        LazyColumn {
            items(sucursales) { sucursal ->
                MapMarkerCard(punto = sucursal)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapaTiendasScreenPreview() {
    val sucursalesFake = listOf(
        PuntoTienda(
            id = "STGO01",
            nombre = "Huerto Hogar Santiago Centro",
            direccion = "Av. Principal 123",
            ciudad = "Santiago",
            latitud = -33.44,
            longitud = -70.66,
            telefono = "+56 2 2345 6789",
            horario = "Lun a Sáb 09:00 – 20:00"
        )
    )

    HuertoHogarTheme {
        MapaTiendasScreen(sucursales = sucursalesFake)
    }
}
