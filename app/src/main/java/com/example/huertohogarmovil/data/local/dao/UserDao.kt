package com.example.huertohogarmovil.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.huertohogarmovil.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UserEntity?

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun countByEmail(email: String): Int

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserById(id: Long): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserByIdOnce(id: Long): UserEntity?

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getLoggedUser(): UserEntity?

    @Insert
    suspend fun registerUser(user: UserEntity)

    @Query("""
        UPDATE users 
        SET name = :name, email = :email, phone = :phone
        WHERE id = :id
    """)
    suspend fun updateUser(
        id: Long,
        name: String,
        email: String,
        phone: String?
    )

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUser(id: Long)
}
