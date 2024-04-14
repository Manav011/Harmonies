package com.example.musicapp

import com.example.musicapp.Search.MyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    @Headers("X-RapidAPI-Key: 3581b63748msh53a47f9f5806ab4p151767jsn7c121457d580 ",
     "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com")
    @GET("search")
    fun getSearchData(@Query("q") query: String) : Call<MyData>
}