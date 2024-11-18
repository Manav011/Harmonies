package com.example.musicapp.map

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.musicapp.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private lateinit var getlocbtn: Button
    private lateinit var fusedlocation: FusedLocationProviderClient

    private var lattitudee:Double=0.0
    private var longitudee:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        getlocbtn = findViewById(R.id.getlocationbtn)
        getlocbtn.setOnClickListener {
            startActivity(Intent(this@MapActivity, Aboutus::class.java))
        }

        fusedlocation = LocationServices.getFusedLocationProviderClient(this)
        val mapfragment=supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapfragment.getMapAsync(this)
    }

    override fun onMapReady(googlemap: GoogleMap) {
            googlemap.clear()
            getLocationFromAddress("Lpu")
            googleMap=googlemap

            val latLng = LatLng(lattitudee,longitudee)
            googlemap.addMarker(
                MarkerOptions()
                    .position(latLng).title("Marker location") )
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 8f)
            googlemap.moveCamera(cameraUpdate)

    }

    private fun getLocationFromAddress(location:String){
        val geocode= Geocoder(this)
        val list:MutableList<Address>?=geocode.getFromLocationName(location,5)
        if(list.isNullOrEmpty()){
            return
        }
        lattitudee=list[0].latitude
        longitudee=list[0].longitude

    }
}