package com.example.musicapp.likedsongs

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.sqllitedb.DBHelper

class LikeActivity : AppCompatActivity() {

    private lateinit var likelist: RecyclerView
    private lateinit var dbHelper: DBHelper
    private lateinit var songAdapter: SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like)

        likelist = findViewById(R.id.likelist)
        dbHelper = DBHelper(this)

        // Set the layout manager to display items in a grid
        likelist.layoutManager = GridLayoutManager(this, 2)

        // Fetch the liked songs from the database
        val likedSongs = dbHelper.getAllFavorites().toMutableList() // Make it mutable for updates

        if (likedSongs.isEmpty()) {
            // Show a toast if no liked songs are found
            Toast.makeText(this, "There are no liked songs", Toast.LENGTH_SHORT).show()
        } else {
            // Create the custom adapter to bind the data to the RecyclerView
            songAdapter = SongAdapter(this, likedSongs, dbHelper)
            likelist.adapter = songAdapter
        }
    }
}


