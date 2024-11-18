package com.example.musicapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.musicapp.Search.Data
import com.example.musicapp.fragments.ListFragment
import com.example.musicapp.fragments.DetailsFragment
import com.example.musicapp.fragments.ViewPagerAdapter
import com.example.musicapp.likedsongs.LikeActivity
import com.example.musicapp.map.MapActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val fragments = listOf(ListFragment(), DetailsFragment()) // Replace with your fragment instances
        val adapter = ViewPagerAdapter(fragments, supportFragmentManager,lifecycle)
        viewPager.adapter = adapter

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Set tab text or icon here if needed
            tab.text = if (position == 0) "Search" else "Details"
            tab.icon = if (position == 0){
                ContextCompat.getDrawable(this@LandingPage, R.drawable.search)
            }else{
                ContextCompat.getDrawable(this@LandingPage, R.drawable.details)
            }
        }.attach()

        val floatingbtnmap: FloatingActionButton = findViewById(R.id.floatingbtnmap)
        floatingbtnmap.setOnClickListener{
            startActivity(Intent(this@LandingPage, MapActivity::class.java))
        }

        val floatingbtnlike: FloatingActionButton = findViewById(R.id.floatingbtnlike)
        floatingbtnlike.setOnClickListener{
            startActivity(Intent(this@LandingPage, LikeActivity::class.java))
        }

    }
}