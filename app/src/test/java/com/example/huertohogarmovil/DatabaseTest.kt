package com.example.huertohogarmovil

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.huertohogarmovil.data.local.HuertoDatabase
import com.example.huertohogarmovil.data.local.entity.ProductoEntity
import com.example.huertohogarmovil.data.local.entity.UserEntity
import com.example.huertohogarmovil.data.local.entity.OrdenEntity
import com.example.huertohogarmovil.data.local.entity.EnvioEntity
import com.example.huertohogarmovil.model.MetodoPago
import com.example.huertohogarmovil.model.OrdenStatus
import com.example.huertohogarmovil.model.CardInfo
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class DatabaseTest : StringSpec({

    // ✔ Obtener contexto para test
    val context = ApplicationProvider.getApplicationContext<android.content.Context>()

    // ✔ Base de datos en memoria (no la real)
    val db = Room.inMemoryDatabaseBuilder(
        context,
        HuertoDatabase::class.java
    ).allowMainThreadQueries().build()

    val userDao = db.userDao()
    val productoDao = db.productoDao()
    val ordenDao = db.ordenDao()

    // ====================================================
    // 1) TEST USER DAO
    // ====================================================
    "UserDao → debe insertar y recuperar usuario" {

        val u = UserEntity(
            id = 0,
            name = "Karla",
            email = "karla@test.com",
            password = "1234",
            phone = "999"
        )

        userDao.registerUser(u)

        val saved = userDao.getUserByIdOnce(1)

        saved?.email shouldBe "karla@test.com"
        saved?.name shouldBe "Karla"
    }

    // ====================================================
    // 2) TEST PRODUCTO DAO
    // ====================================================
    "ProductoDao → debe insertar y obtener producto" {

        val p = ProductoEntity(
            id = 10,
            title = "Lechuga",
            category = "verduras",
            price = 1200,
            stock = 15,
            description = "Fresca",
            thumbnail = "lechuga.png"
        )

        productoDao.insert(p)

        val result = productoDao.getDirect(10)

        result?.title shouldBe "Lechuga"
        result?.price shouldBe 1200
    }

    // ====================================================
    // 3) TEST ORDEN DAO
    // ====================================================
    "OrdenDao → debe insertar y obtener orden" {

        val direccion = EnvioEntity(
            street = "Calle 1",
            addressDetail = "Casa 2",
            city = "Santiago",
            comuna = "Ñuñoa"
        )

        val cardInfo = CardInfo(
            cardNumber = "123456789",
            expiryDate = "12/30",
            cvv = "123",
            cardHolder = "Karla Soto"
        )

        val orden = OrdenEntity(
            id = 5,
            userId = 1,
            direccion = direccion,
            paymentMethod = MetodoPago.EFECTIVO,
            cardNumber = null,
            cardHolder = null,
            createdAt = "2025-01-01",
            costoEnvio = 3990,
            status = OrdenStatus.PENDIENTE,
            cardInfo = cardInfo
        )

        ordenDao.insertarOrden(orden)

        val result = ordenDao.obtenerOrdenPorId(5)

        result?.id shouldBe 5
        result?.userId shouldBe 1
        result?.status shouldBe OrdenStatus.PENDIENTE
    }

})
