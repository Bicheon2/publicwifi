package com.example.publicwifi.api.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("userid") val phone: String,
    @SerializedName("password") val password: String
)

class LoginDtoParams(
    val userid: String,
    val password: String,
)