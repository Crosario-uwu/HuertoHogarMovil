package com.example.huertohogarmovil

import com.example.huertohogarmovil.data.repository.ProductoRepository
import com.example.huertohogarmovil.model.Producto
import com.example.huertohogarmovil.viewmodel.ProductoViewModel
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductoViewModelTest {

    private val repo = mockk<ProductoRepository>()
    private val vm = ProductoViewModel(repo)

    @Test
    fun `obtenerProducto actualiza productoDetalle`() = runTest {

        // ARRANGE - datos falsos
        val productoFalso = Producto(
            id = 1,
            title = "Tomate",
            price = 1200,
            category = "vegetales",
            description = "Tomate rojo",
            stock = 50,
            thumbnail = "tomate.png"
        )


        // ASSERT
        vm.productoDetalle.value shouldBe productoFalso
    }
}