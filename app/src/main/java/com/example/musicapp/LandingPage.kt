package com.example.musicapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.musicapp.fragments.ListFragment
import com.example.musicapp.fragments.LyricsFragment
import com.example.musicapp.fragments.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val fragments = listOf(ListFragment(), LyricsFragment()) // Replace with your fragment instances
        val adapter = ViewPagerAdapter(fragments, supportFragmentManager,lifecycle)
        viewPager.adapter = adapter

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Set tab text or icon here if needed
            tab.text = "Tab ${position + 1}"
            tab.text = if (position == 0) "Search" else "Lyrics"
        }.attach()

    }
}