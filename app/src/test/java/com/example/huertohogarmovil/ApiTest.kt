package com.example.huertohogarmovil

import com.example.huertohogarmovil.data.remote.api.DummyJsonProductApi
import com.example.huertohogarmovil.data.remote.api.UserApiService
import com.example.huertohogarmovil.data.remote.api.OrdenApiService
import com.example.huertohogarmovil.data.remote.dto.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class ApiTest : StringSpec({

    // -------------------------------------------------------
    // 1) PRODUCTOS API TEST
    // -------------------------------------------------------
    "API Productos → debe retornar lista de productos" {

        val api = mockk<DummyJsonProductApi>()

        val fakeResponse = DummyProductListResponse(
            products = listOf(
                DummyProductDto(
                    id = 1,
                    title = "Tomate",
                    description = "Tomate fresco",
                    category = "vegetal",
                    price = 1500,
                    stock = 20,
                    thumbnail = "img1.jpg"
                )
            )
        )

        coEvery { api.getProducts() } returns fakeResponse

        val result = api.getProducts()

        result.products.size shouldBe 1
        result.products.first().title shouldBe "Tomate"
    }


    // -------------------------------------------------------
    // 2) USER API TEST
    // -------------------------------------------------------
    "API Usuario → debe retornar un usuario por ID" {

        val api = mockk<UserApiService>()

        val fakeUser = DummyUserDto(
            id = 5,
            firstName = "Karla",
            lastName = "Soto",
            email = "karla@gmail.com",
            username = "karlaUser",
            image = "perfil.jpg",
            phone = "999999"
        )

        coEvery { api.getUserById(5) } returns fakeUser

        val result = api.getUserById(5)

        result.email shouldBe "karla@gmail.com"
        result.firstName shouldBe "Karla"
    }


    // -------------------------------------------------------
    // 3) ORDEN & CART API TEST
    // -------------------------------------------------------
    "API Orden → debe crear cart correctamente" {

        val api = mockk<OrdenApiService>()

        val fakeOrder = DummyCartDto(
            id = 10,
            products = listOf(),
            total = 5000,
            discountedTotal = 4000,
            userId = 1,
            totalProducts = 1,
            totalQuantity = 2
        )

        coEvery { api.createCart(any()) } returns fakeOrder

        val result = api.createCart(
            DummyCartRequest(
                userId = 1,
                products = listOf(
                    CartProductItem(id = 1, quantity = 2)
                )
            )
        )

        result.id shouldBe 10
        result.total shouldBe 5000
        result.userId shouldBe 1
    }
})
