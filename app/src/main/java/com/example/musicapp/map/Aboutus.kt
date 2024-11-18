package com.example.musicapp.map

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.musicapp.R

class Aboutus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aboutus)

        val gridView = findViewById<GridView>(R.id.gridview)

        val Imagelist = ArrayList<Int>()

        Imagelist.add(R.drawable.youtube)
        Imagelist.add(R.drawable.facebook)
        Imagelist.add(R.drawable.instagram)
        Imagelist.add(R.drawable.whatsapp)
        Imagelist.add(R.drawable.twitter)
        Imagelist.add(R.drawable.spotify)
        Imagelist.add(R.drawable.mail)

        val name = arrayOf("Youtube", "Facebook", "Instagram", "Whatsapp", "Twitter", "Spotify", "Gmail")

        gridView.adapter = CustomGridViewAdapter(this@Aboutus, Imagelist, name)

        gridView.setOnItemClickListener{adapterView, parent, position, id ->
            if (position == 0) {
                Toast.makeText(this@Aboutus, "Redirecting to Youtube", Toast.LENGTH_LONG).show()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/user/EdSheeran")))
            }
            else if (position == 1){
                Toast.makeText(this@Aboutus, "Redirecting to Facebook", Toast.LENGTH_LONG).show()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/EdSheeranMusic/")))
            }
            else if (position == 2){
                Toast.makeText(this@Aboutus, "Redirecting to Instagram", Toast.LENGTH_LONG).show()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/teddysphotos/")))
            }
            else if (position == 3){
                Toast.makeText(this@Aboutus, "Redirecting to Whatsapp", Toast.LENGTH_LONG).show()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.whatsapp.com")))
            }
            else if (position == 4){
                Toast.makeText(this@Aboutus, "Redirecting to X", Toast.LENGTH_LONG).show()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/edsheeran/")))
            }
            else if (position == 5){
                Toast.makeText(this@Aboutus, "Redirecting to Spotify", Toast.LENGTH_LONG).show()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/artist/6eUKZXaKkcviH0Ku9w2n3V")))
            }
            else{
                Toast.makeText(this@Aboutus, "Redirecting to Mail", Toast.LENGTH_LONG).show()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://gmail.com")))
            }

        }

    }
}