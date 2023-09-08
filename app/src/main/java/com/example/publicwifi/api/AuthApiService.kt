package com.example.publicwifi.api

import com.example.publicwifi.api.dto.LoginDtoParams
import com.example.publicwifi.api.dto.SignUpDtoParams
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    // 로그인
    @POST("login")
    fun login(@Body jsonParams: LoginDtoParams): Call<String?>

    // 회원가입
    @POST("signup")
    fun signUp(@Body jsonParams: SignUpDtoParams): Call<String?>

}