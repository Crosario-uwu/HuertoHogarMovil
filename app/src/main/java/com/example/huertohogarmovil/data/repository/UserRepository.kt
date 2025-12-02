package com.example.huertohogarmovil.data.repository

import com.example.huertohogarmovil.data.local.dao.UserDao
import com.example.huertohogarmovil.data.local.entity.UserEntity
import com.example.huertohogarmovil.data.remote.ApiClient
import com.example.huertohogarmovil.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao     // INYECCIÓN DE DEPENDENCIA
) {

    private val api = ApiClient.userApi   // API DummyJson

    // ------------------------------------
    // LOGIN (Room)
    // ------------------------------------
    suspend fun login(email: String, password: String): User? {
        val entity = userDao.login(email, password)
        return entity?.toModel()
    }

    // ------------------------------------
    // REGISTRO (Room)
    // -----------------------------------
    suspend fun registrarUsuario(user: User) {
        val entity = UserEntity(
            email = user.email,
            name = user.name,
            phone = user.phone,
            password = user.password
        )
        userDao.registerUser(entity)
    }

    // ------------------------------------
    // LISTAR DESDE API (DummyJson)
    // ------------------------------------
    suspend fun obtenerUsuariosRemoto(): List<User> {
        return api.getUsers().map {
            User(
                id = it.id.toLong(),
                name = "${it.firstName} ${it.lastName}",
                email = it.email,
                phone = it.phone ?: "",
                password = ""
            )
        }
    }

    // ------------------------------------
    // OBTENER DETALLE DESDE API
    // ------------------------------------
    suspend fun obtenerUsuarioRemoto(id: Long): User {
        val u = api.getUserById(id.toInt())
        return User(
            id = u.id.toLong(),
            name = "${u.firstName} ${u.lastName}",
            email = u.email,
            phone = u.phone ?: "",
            password =""
        )
    }

    // ------------------------------------
    // LISTAR (Room + Flow)
    // ------------------------------------
    fun obtenerUsuariosLocal(): Flow<List<User>> {
        return userDao.getAllUsers().mapList()
    }

    // ------------------------------------
    // OBTENER
    // ------------------------------------
    fun obtenerUsuarioLocal(id: Long): Flow<User?> {
        return userDao.getUserById(id).mapNullable()
    }

    // ------------------------------------
    // ACTUALIZAR  (Room)
    // ------------------------------------
    suspend fun actualizarUsuario(id: Long, nombre: String, email: String, telefono: String?) {
        userDao.updateUser(
            id = id,
            name = nombre,
            email = email,
            phone = telefono
        )
    }

    // ------------------------------------
    // ELIMINAR  (Room)
    // ------------------------------------
    suspend fun eliminarUsuario(id: Long) {
        userDao.deleteUser(id)
    }
}
