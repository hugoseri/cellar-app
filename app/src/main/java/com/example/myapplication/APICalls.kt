package com.example.myapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APICalls {
    @GET("cellars/{cellarId}/bottles")
    fun getAllBottles(@Path("cellarId") cellarId: String): Call<ArrayList<Bottle>>

    @POST("cellars/{cellarId}/bottles")
    fun addBottle(@Path("cellarId") cellarId: String, @Body bottle :Bottle) : Call<Bottle>
}