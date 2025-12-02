package com.example.huertohogarmovil.data.local.converters

import androidx.room.TypeConverter
import com.example.huertohogarmovil.model.CardInfo
import com.example.huertohogarmovil.model.MetodoPago
import com.example.huertohogarmovil.model.OrdenStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromCardInfo(cardInfo: CardInfo?): String? {
        return gson.toJson(cardInfo)
    }

    @TypeConverter
    fun toCardInfo(data: String?): CardInfo? {
        if (data == null) return null
        val type = object : TypeToken<CardInfo>() {}.type
        return gson.fromJson(data, type)
    }
    @TypeConverter
    fun fromMetodoPago(value: MetodoPago): String = value.name

    @TypeConverter
    fun toMetodoPago(value: String): MetodoPago = MetodoPago.valueOf(value)

    @TypeConverter
    fun fromOrdenStatus(value: OrdenStatus): String = value.name

    @TypeConverter
    fun toOrdenStatus(value: String): OrdenStatus = OrdenStatus.valueOf(value)
}
