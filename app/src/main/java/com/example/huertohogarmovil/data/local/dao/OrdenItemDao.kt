package com.example.huertohogarmovil.data.local.dao

import androidx.room.*
import com.example.huertohogarmovil.data.local.entity.OrdenItemEntity

@Dao
interface OrdenItemDao {

    @Insert
    suspend fun insertarItem(item: OrdenItemEntity)

    @Query("SELECT * FROM orden_items WHERE ordenId = :ordenId")
    suspend fun obtenerItems(ordenId: Long): List<OrdenItemEntity>

    @Query("SELECT * FROM orden_items WHERE ordenId = :ordenId")
    suspend fun obtenerItemsOrden(ordenId: Long): List<OrdenItemEntity>

}
