package com.example.huertohogarmovil.data.local.dao

import androidx.room.*
import com.example.huertohogarmovil.data.local.entity.OrdenEntity

@Dao
interface OrdenDao {

    @Insert
    suspend fun insertarOrden(orden: OrdenEntity): Long

    @Query("SELECT * FROM ordenes WHERE userId = :userId")
    suspend fun obtenerOrdenesUsuario(userId: Long): List<OrdenEntity>

    @Query("SELECT * FROM ordenes")
    suspend fun obtenerTodas(): List<OrdenEntity>

    @Query("SELECT * FROM ordenes WHERE id = :id LIMIT 1")
    suspend fun obtenerOrdenPorId(id: Long): OrdenEntity?

    @Update
    suspend fun actualizarEstado(orden: OrdenEntity)
}
