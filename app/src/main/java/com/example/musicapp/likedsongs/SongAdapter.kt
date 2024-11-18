package com.example.musicapp.likedsongs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.R
import com.example.musicapp.sqllitedb.DBHelper
import com.example.musicapp.sqllitedb.Song
import com.squareup.picasso.Picasso

class SongAdapter(
    private val context: Context,
    private val songList: MutableList<Song>,
    private val dbHelper: DBHelper
) : RecyclerView.Adapter<SongAdapter.MyHolder>() {

    private fun removeSong(position: Int) {
        val songToRemove = songList[position]
        dbHelper.removeSong(songToRemove.title)
        songList.removeAt(position)
        notifyItemRemoved(position)
        Toast.makeText(context, "${songToRemove.title} removed from favorites", Toast.LENGTH_SHORT).show()
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songImage: ImageView = view.findViewById(R.id.likeitemimage)
        val songTitle: TextView = view.findViewById(R.id.likeitemname)
        val songDuration: TextView = view.findViewById(R.id.likeitemtime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.like_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val song = songList[position]

        Picasso.get().load(song.albumCover).into(holder.songImage)
        holder.songTitle.text = song.title

        val minutes = song.duration / 60
        val seconds = if (song.duration % 60 >= 10) song.duration % 60 else "0${song.duration % 60}"
        holder.songDuration.text = "$minutes:$seconds"

        holder.itemView.setOnClickListener {
            removeSong(position)
        }
    }

    // Return the total count of items
    override fun getItemCount(): Int = songList.size

}
