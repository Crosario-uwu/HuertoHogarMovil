package com.example.huertohogarmovil.data.repository

import com.example.huertohogarmovil.data.local.dao.CarritoDao
import com.example.huertohogarmovil.data.local.dao.OrdenDao
import com.example.huertohogarmovil.data.local.dao.OrdenItemDao
import com.example.huertohogarmovil.data.local.entity.EnvioEntity
import com.example.huertohogarmovil.data.local.entity.OrdenEntity
import com.example.huertohogarmovil.data.local.entity.OrdenItemEntity
import com.example.huertohogarmovil.data.remote.ApiClient
import com.example.huertohogarmovil.data.remote.dto.CartProductItem
import com.example.huertohogarmovil.data.remote.dto.DummyCartRequest
import com.example.huertohogarmovil.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class OrdenRepository(
    private val ordenDao: OrdenDao,
    private val ordenItemDao: OrdenItemDao,
    private val carritoDao: CarritoDao
) {

    private val api = ApiClient.ordenApi

    // ============================================
    // CREAR ORDEN
    // ============================================
    suspend fun crearOrden(
        userId: Long,
        datosEnvio: DatosEnvio,
        metodoPago: MetodoPago,
        cardInfo: CardInfo?,
        items: List<CartItem>
    ): Long {

        val request = DummyCartRequest(
            userId = userId.toInt(),
            products = items.map {
                CartProductItem(
                    id = it.producto.id,
                    quantity = it.quantity
                )
            }
        )

        val remote = api.createCart(request)

        val fecha = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val ordenId = remote.id.toLong()

        withContext(Dispatchers.IO) {

            ordenDao.insertarOrden(
                OrdenEntity(
                    id = ordenId,
                    userId = userId,
                    direccion = datosEnvio.toEntity(),
                    paymentMethod = metodoPago,
                    cardNumber = cardInfo?.cardNumber,
                    cardHolder = cardInfo?.cardHolder,
                    createdAt = fecha,
                    costoEnvio = 3990,
                    status = OrdenStatus.PENDIENTE,
                    cardInfo
                )
            )

            // ITEMS
            remote.products.forEach { p ->
                val local = items.firstOrNull { it.producto.id == p.id }

                ordenItemDao.insertarItem(
                    OrdenItemEntity(
                        ordenId = ordenId,
                        productId = p.id,
                        productName = local?.producto?.title ?: "",
                        price = local?.producto?.price ?: 0,
                        quantity = p.quantity,
                        imageName = local?.producto?.thumbnail ?: ""
                    )
                )
            }

            carritoDao.limpiarCarrito()
        }

        return ordenId
    }
    fun EnvioEntity.toModel(): DatosEnvio =
        DatosEnvio(
            street = this.street,
            addressDetail = this.addressDetail,
            city = this.city,
            comuna = this.comuna
        )

    // ============================================
    // LISTA DE ÓRDENES
    // ============================================
    suspend fun obtenerOrdenesUsuario(userId: Long): List<OrdenEntity> {
        return ordenDao.obtenerOrdenesUsuario(userId)
    }


    // =======================================================
// ✔ Obtener Orden por ID
// =======================================================
    suspend fun obtenerOrdenPorId(id: Long): OrdenEntity {
        return ordenDao.obtenerOrdenPorId(id)!!
    }
    // ============================================
    // ✔ CORRECTO: OBTENER ITEMS DE ORDEN
    // ============================================
    suspend fun obtenerItemsOrden(idOrden: Long): List<OrdenItemEntity> {
        return ordenItemDao.obtenerItemsOrden(idOrden)
    }


    // ============================================
    // ACTUALIZAR ESTADO
    // ============================================
    suspend fun actualizarEstado(idOrden: Long, nuevoEstado: OrdenStatus) {
        val orden = ordenDao.obtenerOrdenPorId(idOrden)
            ?: return   // ⚠ si no existe, no hacemos nada

        val actualizada = orden.copy(status = nuevoEstado)

        ordenDao.actualizarEstado(actualizada)
    }

}
