package com.example.huertohogarmovil.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.huertohogarmovil.data.local.converters.Converters
import com.example.huertohogarmovil.data.local.dao.*
import com.example.huertohogarmovil.data.local.entity.*

@Database(
    entities = [
        ProductoEntity::class,
        UserEntity::class,
        CarritoEntity::class,
        OrdenEntity::class,
        OrdenItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class HuertoDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun userDao(): UserDao
    abstract fun carritoDao(): CarritoDao
    abstract fun ordenDao(): OrdenDao
    abstract fun ordenItemDao(): OrdenItemDao

    companion object {

        @Volatile
        private var INSTANCE: HuertoDatabase? = null

        fun getInstance(context: Context): HuertoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HuertoDatabase::class.java,
                    "huerto_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }


}
