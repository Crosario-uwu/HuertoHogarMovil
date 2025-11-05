package com.example.huertohogarapp.repository

import com.example.huertohogarapp.model.Categoria
import com.example.huertohogarapp.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Maneja el catálogo de productos de Huerto Hogar.
 * Por ahora usa datos en memoria. Luego puedes conectar a Firebase.
 */
class ProductoRepository {
    // Catálogo en memoria (mock basado en el caso Huerto Hogar)
    private val _productos = MutableStateFlow<List<Producto>>(
        listOf(
            Producto(
                id = "FR001",
                nombre = "Manzanas Fuji",
                descripcion = "Manzanas crujientes y dulces del Valle del Maule.",
                precio = 1200,
                categoria = Categoria.FRUTAS,
                origen = "Valle del Maule",
                stock = 150,
                destacado = true
            ),
            Producto(
                id = "FR002",
                nombre = "Naranjas Valencia",
                descripcion = "Jugosas, ideales para jugos ricos en vitamina C.",
                precio = 1000,
                categoria = Categoria.FRUTAS,
                origen = "Región de Coquimbo",
                stock = 200
            ),
            Producto(
                id = "FR003",
                nombre = "Plátanos Cavendish",
                descripcion = "Plátanos maduros y energéticos, perfectos para el desayuno.",
                precio = 800,
                categoria = Categoria.FRUTAS,
                origen = "Región de Los Ríos",
                stock = 250
            ),
            Producto(
                id = "VR001",
                nombre = "Zanahorias Orgánicas",
                descripcion = "Zanahorias sin pesticidas, ricas en vitamina A.",
                precio = 900,
                categoria = Categoria.VERDURAS,
                origen = "Región de O'Higgins",
                stock = 100
            ),
            Producto(
                id = "VR002",
                nombre = "Espinacas Frescas",
                descripcion = "Espinacas ideales para ensaladas y batidos verdes.",
                precio = 700,
                categoria = Categoria.VERDURAS,
                origen = "Región Metropolitana",
                stock = 80
            ),
            Producto(
                id = "VR003",
                nombre = "Pimientos Tricolores",
                descripcion = "Pimientos rojos, amarillos y verdes muy coloridos.",
                precio = 1500,
                categoria = Categoria.VERDURAS,
                origen = "Región del Biobío",
                stock = 120
            ),
            Producto(
                id = "PO001",
                nombre = "Miel Orgánica",
                descripcion = "Miel pura de apicultores locales.",
                precio = 5000,
                categoria = Categoria.ORGANICOS,
                origen = "Zona centro-sur",
                stock = 50
            ),
            Producto(
                id = "PO003",
                nombre = "Quinua Orgánica",
                descripcion = "Quinua perfecta para ensaladas y bowls saludables.",
                precio = 3500,
                categoria = Categoria.ORGANICOS,
                origen = "Altiplano",
                stock = 60
            ),
            Producto(
                id = "PL001",
                nombre = "Leche Entera",
                descripcion = "Leche fresca de granjas locales.",
                precio = 1200,
                categoria = Categoria.LACTEOS,
                origen = "Zona central",
                stock = 80
            )
        )
    )

    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    fun getProductoPorId(id: String): Producto? =
        _productos.value.firstOrNull { it.id == id }

    fun getProductosPorCategoria(categoria: Categoria): List<Producto> =
        _productos.value.filter { it.categoria == categoria }

    // TODO Firebase:
    // - Reemplazar datos mock por lectura desde Firestore
    // - Escuchar cambios en tiempo real y actualizar _productos.value
}
