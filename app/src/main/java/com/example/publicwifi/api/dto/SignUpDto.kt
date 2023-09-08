package com.example.publicwifi.api.dto

import com.google.gson.annotations.SerializedName

data class SignUpDto(
    @SerializedName("userid") val phone: String,
    @SerializedName("password") val password: String,
    @SerializedName("phone_number") val name: String,
)

class SignUpDtoParams(
    val userid: String,
    val password: String,
    val phone_number: String,
)
