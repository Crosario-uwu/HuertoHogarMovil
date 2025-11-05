package com.example.huertohogarapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.huertohogarapp.model.PuntoTienda
import com.example.huertohogarapp.ui.theme.HuertoHogarTheme

/**
 * Tarjeta pequeña para mostrar una sucursal en el mapa.
 */
@Composable
fun MapMarkerCard(
    punto: PuntoTienda,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(punto.nombre, style = MaterialTheme.typography.titleSmall)
            Text(punto.ciudad, style = MaterialTheme.typography.bodySmall)
            Text(punto.telefono, style = MaterialTheme.typography.labelSmall)
            Text(punto.horario, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapMarkerCardPreview() {
    HuertoHogarTheme {
        MapMarkerCard(
            punto = PuntoTienda(
                id = "STGO01",
                nombre = "Huerto Hogar Santiago Centro",
                ciudad = "Santiago",
                telefono = "+56 2 2345 6789",
                horario = "Lun a Sáb 09:00 – 20:00"
            )
        )
    }
}
