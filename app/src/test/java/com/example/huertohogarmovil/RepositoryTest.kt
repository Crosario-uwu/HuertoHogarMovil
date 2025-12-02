package com.example.huertohogarmovil

import com.example.huertohogarmovil.data.local.dao.*
import com.example.huertohogarmovil.data.local.entity.*
import com.example.huertohogarmovil.data.repository.ProductoRepository
import com.example.huertohogarmovil.data.repository.UserRepository
import com.example.huertohogarmovil.data.repository.OrdenRepository
import com.example.huertohogarmovil.model.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class RepositoryTest : StringSpec({

    // ======================================================
    // PRODUCTO REPOSITORY
    // ======================================================
    "ProductoRepository → debe convertir entity a modelo" {
        runTest {

            val dao = mockk<ProductoDao>()

            // ❗ getDirect ES suspend → usar coEvery
            coEvery { dao.getDirect(1) } returns ProductoEntity(
                id = 1,
                title = "Tomate",
                category = "verduras",
                price = 1500,
                stock = 20,
                description = "Rojo",
                thumbnail = "tomate.png"
            )

            val repo = ProductoRepository(dao)

            val result = repo.obtenerProducto(1)

            result?.title shouldBe "Tomate"
            result?.price shouldBe 1500
        }
    }

    // ======================================================
    // USER REPOSITORY
    // ======================================================
    "UserRepository → debe mapear entity a modelo" {
        runTest {

            val dao = mockk<UserDao>()

            coEvery { dao.login("test@test.com", "1234") } returns UserEntity(
                id = 20,
                name = "Pedro",
                email = "test@test.com",
                password = "1234",
                phone = "777"
            )

            val repo = UserRepository(dao)

            val user = repo.login("test@test.com", "1234")

            user?.name shouldBe "Pedro"
            user?.email shouldBe "test@test.com"
        }
    }


    // ======================================================
    // ORDEN REPOSITORY
    // ======================================================
    "OrdenRepository → debe obtener orden por ID" {
        runTest {

            val ordenDao = mockk<OrdenDao>()
            val itemDao = mockk<OrdenItemDao>()
            val carritoDao = mockk<CarritoDao>()

            val direccion = EnvioEntity(
                street = "Calle 1",
                addressDetail = "Casa 2",
                city = "Santiago",
                comuna = "Ñuñoa"
            )

            val cardInfo = CardInfo(
                cardNumber = "12345678",
                expiryDate = "12/30",
                cvv = "123",
                cardHolder = "Karla Soto"
            )

            // ❗ obtenerOrdenPorId ES suspend → usar coEvery
            coEvery { ordenDao.obtenerOrdenPorId(10) } returns OrdenEntity(
                id = 10,
                userId = 2,
                direccion = direccion,
                paymentMethod = MetodoPago.EFECTIVO,
                cardNumber = null,
                cardHolder = null,
                createdAt = "2024",
                costoEnvio = 3990,
                status = OrdenStatus.PENDIENTE,
                cardInfo = cardInfo
            )

            val repo = OrdenRepository(ordenDao, itemDao, carritoDao)

            val orden = repo.obtenerOrdenPorId(10)

            orden?.id shouldBe 10
            orden?.userId shouldBe 2
        }
    }
})
