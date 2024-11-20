package com.example.musicapp.sqllitedb

data class Song(
    val id: Int,
    val title: String,
    val duration: Int,
    val albumCover: String,
    val likedDate: String? = null,
    val previewUrl: String? = null
)
