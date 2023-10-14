package com.example.publicwifi.api.dto

import com.google.gson.annotations.SerializedName

data class WifiDto(
    @SerializedName("ssid") val ssid: String,
    @SerializedName("ccu") val ccu: String,
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String,
    @SerializedName("addr_state") val addr_state: String,
    @SerializedName("addr_city") val addr_city: String,
    @SerializedName("addr_detail") val addr_detail: String,
    @SerializedName("ip_addr") val ip_addr: String,
)

class WifiDtoParams(
    val ssid: String,
    val lat: Double,
    val lng: Double,
    val addr_state: String,
    val addr_city: String,
    val addr_detail: String,
    val ip_addr: String,
)