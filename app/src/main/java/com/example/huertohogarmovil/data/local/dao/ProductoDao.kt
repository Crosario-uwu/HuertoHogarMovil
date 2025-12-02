package com.example.huertohogarmovil.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.huertohogarmovil.data.local.entity.ProductoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    @Query("SELECT * FROM productos")
    fun getAll(): Flow<List<ProductoEntity>>

    @Query("SELECT COUNT(*) FROM productos")
    suspend fun countProductos(): Int

    @Query("SELECT * FROM productos WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): ProductoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: ProductoEntity)

    @Update
    suspend fun update(producto: ProductoEntity)

    @Query("DELETE FROM productos WHERE id = :id")
    suspend fun delete(id: Int)
}
