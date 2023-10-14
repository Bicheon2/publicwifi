package com.example.publicwifi.api

import com.example.publicwifi.api.dto.WifiDto
import com.example.publicwifi.api.dto.WifiDtoParams
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.GET

interface WifiApiService {

    @POST("wifi")
    fun postWifi(@Body jsonParams: WifiDtoParams): Call<WifiDto?>

    @GET("wifi")
    fun getWifi(): Call<List<WifiDto?>>
}