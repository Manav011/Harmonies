package com.example.musicapp.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE id = 1")
    suspend fun getUserById(): User?

    @Query("SELECT EXISTS(SELECT 1 FROM users LIMIT 1)")
    suspend fun isUserExists(): Boolean
}
