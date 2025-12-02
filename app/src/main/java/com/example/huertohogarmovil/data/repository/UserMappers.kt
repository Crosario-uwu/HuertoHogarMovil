package com.example.huertohogarmovil.data.repository

import com.example.huertohogarmovil.data.local.entity.UserEntity
import com.example.huertohogarmovil.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun UserEntity.toModel(): User = User(
    id = id,
    name = name,
    email = email,
    phone = phone ?: "",
    password = password
)

fun Flow<List<UserEntity>>.mapList(): Flow<List<User>> =
    this.map { list -> list.map { it.toModel() } }

fun Flow<UserEntity?>.mapNullable(): Flow<User?> =
    this.map { it?.toModel() }
