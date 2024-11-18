package com.example.musicapp.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int = 1,
    val username: String,
    val pin: String
)
