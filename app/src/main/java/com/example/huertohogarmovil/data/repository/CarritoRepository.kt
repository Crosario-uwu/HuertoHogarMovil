package com.example.huertohogarmovil.data.repository

import com.example.huertohogarmovil.data.local.dao.CarritoDao
import com.example.huertohogarmovil.data.local.entity.CarritoEntity
import com.example.huertohogarmovil.data.remote.ApiClient
import com.example.huertohogarmovil.data.remote.api.OrdenApiService
import com.example.huertohogarmovil.data.remote.dto.CartProductItem
import com.example.huertohogarmovil.data.remote.dto.DummyCartRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CarritoRepository(
    private val dao: CarritoDao
) {

    private val api: OrdenApiService = ApiClient.ordenApi

    // ============================================================
    // ✔ CARGAR CARRITO REAL DESDE API Y GUARDAR EN ROOM
    // ============================================================
    suspend fun cargarCarritoDesdeApi(userId: Int) {

        val response = api.getCart(userId)

        val items = response.products.map { p ->
            CarritoEntity(
                productCode = p.id.toString(),
                name = p.title,
                price = p.price,
                quantity = p.quantity,
                imageName = p.id.toString()  // Usar id si no tienes imagen fija
            )
        }

        withContext(Dispatchers.IO) {
            dao.limpiarCarrito()
            items.forEach { dao.agregarItem(it) }
        }
    }

    // ============================================================
    // ✔ OBTENER CARRITO LOCAL
    // ============================================================
    suspend fun obtenerCarritoLocal(): List<CarritoEntity> {
        return dao.obtenerCarrito()
    }

    // ============================================================
    // ✔ AGREGAR ITEM LOCAL + API
    // ============================================================
    suspend fun agregarProducto(userId: Int, producto: CarritoEntity) {

        api.createCart(
            DummyCartRequest(
                userId = userId,
                products = listOf(
                    CartProductItem(
                        id = producto.productCode.toInt(),
                        quantity = producto.quantity
                    )
                )
            )
        )

        dao.agregarItem(producto)
    }

    // ============================================================
    // ✔ ACTUALIZAR CANTIDAD
    // ============================================================
    suspend fun actualizarCantidad(productId: String, cantidad: Int) {

        val actual = dao.obtenerCarrito().find { it.productCode == productId }
            ?: return

        val actualizado = actual.copy(quantity = cantidad)

        dao.actualizarItem(actualizado)
    }

    // ============================================================
    // ✔ LIMPIAR CARRITO
    // ============================================================
    suspend fun limpiar() {
        dao.limpiarCarrito()
    }
}
