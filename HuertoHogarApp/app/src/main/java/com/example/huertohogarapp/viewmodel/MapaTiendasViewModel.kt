package com.example.huertohogarapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.huertohogarapp.model.PuntoTienda
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MapaTiendasViewModel : ViewModel() {

    // Datos de sucursales basados en el contexto del caso
    private val _puntosTiendas = MutableStateFlow<List<PuntoTienda>>(
        listOf(
            PuntoTienda(
                id = "STGO01",
                nombre = "Huerto Hogar - Santiago Centro",
                direccion = "Av. Principal 123, Santiago Centro",
                ciudad = "Santiago",
                latitud = -33.4489,
                longitud = -70.6693,
                telefono = "+56 2 2345 6789",
                horario = "Lun a Sáb 09:00 - 20:00"
            ),
            PuntoTienda(
                id = "VINA01",
                nombre = "Huerto Hogar - Viña del Mar",
                direccion = "Calle Marina 456, Viña del Mar",
                ciudad = "Viña del Mar",
                latitud = -33.0245,
                longitud = -71.5518,
                telefono = "+56 32 234 5678",
                horario = "Lun a Sáb 10:00 - 19:00"
            ),
            PuntoTienda(
                id = "VALP01",
                nombre = "Huerto Hogar - Valparaíso",
                direccion = "Cerro Alegre 789, Valparaíso",
                ciudad = "Valparaíso",
                latitud = -33.0472,
                longitud = -71.6127,
                telefono = "+56 32 298 7654",
                horario = "Lun a Sáb 10:00 - 18:00"
            )
        )
    )
    val puntosTiendas: StateFlow<List<PuntoTienda>> = _puntosTiendas
}
