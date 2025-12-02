package com.example.huertohogarmovil.data.repository

import com.example.huertohogarmovil.data.local.dao.ProductoDao
import com.example.huertohogarmovil.data.local.entity.ProductoEntity
import com.example.huertohogarmovil.model.Producto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductoRepository(
    private val dao: ProductoDao
) {

    // ---------------------------------------------------------
    // INSERTAR PRODUCTOS INICIALES SOLO UNA VEZ
    // ---------------------------------------------------------
    suspend fun cargarProductosLocalesIniciales() {

        val lista = listOf(
            ProductoEntity(title="Zanahoria",price=890,category="Verduras",description="Fresca",stock=20,thumbnail="zanahoria"),
            ProductoEntity(title="Uvas Verdes",price=1590,category="Frutas",description="Dulces",stock=20,thumbnail="uvasverdes"),
            ProductoEntity(title="Pimientos",price=1350,category="Verduras",description="Rojos",stock=20,thumbnail="pimientos"),
            ProductoEntity(title="Espinaca",price=990,category="Verduras",description="Fresca",stock=20,thumbnail="espinaca"),
            ProductoEntity(title="Yogurt",price=1190,category="Lácteos",description="Natural",stock=20,thumbnail="yougurt"),
            ProductoEntity(title="Manzana Fuji",price=790,category="Frutas",description="Roja",stock=20,thumbnail="manzanafuji"),
            ProductoEntity(title="Naranja",price=690,category="Frutas",description="Jugosa",stock=20,thumbnail="naranja"),
            ProductoEntity(title="Leche",price=1100,category="Lácteos",description="Entera",stock=20,thumbnail="leche"),
            ProductoEntity(title="Quinoa",price=2500,category="Granos",description="Premium",stock=20,thumbnail="quinoa"),
            ProductoEntity(title="Plátano",price=650,category="Frutas",description="Fresco",stock=20,thumbnail="platano"),
            ProductoEntity(title="Miel",price=3200,category="Natural",description="Orgánica",stock=20,thumbnail="miel")
        )

        lista.forEach { dao.insert(it) }
    }

    suspend fun countProductos(): Int = dao.countProductos()

    // ---------------------------------------------------------
    // OBTENER LISTA
    // ---------------------------------------------------------
    fun obtenerProductos(): Flow<List<Producto>> =
        dao.getAll().map { list -> list.map { it.toModel() } }

    // ---------------------------------------------------------
    // DETALLE
    // ---------------------------------------------------------
    suspend fun obtenerProducto(id: Int): Producto? =
        dao.getById(id)?.toModel()

    // ---------------------------------------------------------
    // CREAR
    // ---------------------------------------------------------
    suspend fun crearProducto(
        title: String,
        price: Int,
        category: String,
        description: String,
        stock: Int,
        thumbnail: String
    ) {
        dao.insert(
            ProductoEntity(
                title = title,
                price = price,
                category = category,
                description = description,
                stock = stock,
                thumbnail = thumbnail
            )
        )
    }

    // ---------------------------------------------------------
    // ACTUALIZAR
    // ---------------------------------------------------------
    suspend fun actualizarProducto(
        id: Int,
        title: String,
        price: Int,
        category: String,
        description: String,
        stock: Int,
        thumbnail: String?
    ) {
        dao.update(
            ProductoEntity(
                id = id,
                title = title,
                price = price,
                category = category,
                description = description,
                stock = stock,
                thumbnail = thumbnail
            )
        )
    }

    // ---------------------------------------------------------
    // ELIMINAR
    // ---------------------------------------------------------
    suspend fun eliminarProducto(id: Int) = dao.delete(id)

    // ---------------------------------------------------------
    // ENTITY → MODEL
    // ---------------------------------------------------------
    private fun ProductoEntity.toModel() = Producto(
        id = id,
        title = title,
        category = category,
        price = price,
        stock = stock,
        description = description,
        thumbnail = thumbnail
    )
}
