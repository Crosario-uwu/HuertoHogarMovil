package com.example.huertohogarmovil.data.local.dao

import androidx.room.*
import com.example.huertohogarmovil.data.local.entity.CarritoEntity

@Dao
interface CarritoDao {

    @Query("SELECT * FROM carrito")
    suspend fun obtenerCarrito(): List<CarritoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarItem(item: CarritoEntity)

    @Update
    suspend fun actualizarItem(item: CarritoEntity)

    @Delete
    suspend fun eliminarItem(item: CarritoEntity)

    @Query("DELETE FROM carrito")
    suspend fun limpiarCarrito()
}
