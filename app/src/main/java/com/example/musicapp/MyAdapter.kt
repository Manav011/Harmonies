package com.example.musicapp

import android.app.Activity
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.Search.Data
import com.example.musicapp.Search.MyData
import com.example.musicapp.sqllitedb.DBHelper
import com.squareup.picasso.Picasso

class MyAdapter(val context: Activity, val dataList: List<Data>):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create the view in case the layout manager fails to create the view for the data
        val itemView = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // populating the data in the view
        val currentData = dataList[position]
        val mediaPlayer = MediaPlayer.create(context, currentData.preview.toUri())

        holder.title.text = currentData.title
        val minutes = currentData.duration / 60
        val seconds = if (currentData.duration % 60 >= 10) currentData.duration % 60 else "0${currentData.duration % 60}"
        holder.duration.text = "${minutes}:${seconds}"
        Picasso.get().load(currentData.album.cover).into(holder.image);

        holder.play.setOnClickListener {
            mediaPlayer.start()
        }

        holder.pause.setOnClickListener {
            mediaPlayer.stop()
        }

        holder.like.setOnClickListener{
            val dbHelper = DBHelper(context)

            val isAdded = dbHelper.addFavoriteSong(
                currentData.title,
                currentData.duration,
                currentData.album.cover
            )

            if(isAdded){
                Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Already in Favorites", Toast.LENGTH_SHORT).show()
            }
        }
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView
        val title: TextView
        val play: ImageView
        val pause: ImageView
        val like: ImageView
        val duration: TextView

        init {
            image = itemView.findViewById(R.id.musicimage)
            title = itemView.findViewById(R.id.musictitle)
            play = itemView.findViewById(R.id.btnPlay)
            pause = itemView.findViewById(R.id.btnPause)
            like = itemView.findViewById(R.id.btnLike)
            duration = itemView.findViewById(R.id.duration)
        }
    }

}