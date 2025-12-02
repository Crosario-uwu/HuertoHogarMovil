package com.example.huertohogarmovil.model

data class Orden(
    val id: Long = 0,
    val userId: Long,
    val items: List<CartItem>,

    // DATOS DE ENVÍO
    val datosEnvio: DatosEnvio,

    // PAGO
    val paymentMethod: MetodoPago,
    val cardInfo: CardInfo? = null, // Solo si paymentMethod = TARJETA

    // ESTADO DEL PEDIDO
    val status: OrdenStatus = OrdenStatus.PENDIENTE,

    // FECHA
    val createdAt: String,

    // COSTOS
    val costoEnvio: Int = 3990
) {
    val productsTotal: Int get() = items.sumOf { it.subtotal }
    val totalToPay: Int get() = productsTotal + costoEnvio
}