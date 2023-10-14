package com.example.publicwifi.api

import com.example.publicwifi.api.dto.LoginDto
import com.example.publicwifi.api.dto.LoginDtoParams
import com.example.publicwifi.api.dto.SignUpDto
import com.example.publicwifi.api.dto.SignUpDtoParams
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    // 로그인
    @POST("auth/login")
    fun login(@Body jsonParams: LoginDtoParams): Call<LoginDto?>

    // 회원가입
    @POST("auth/register")
    fun signUp(@Body jsonParams: SignUpDtoParams): Call<SignUpDto?>

}