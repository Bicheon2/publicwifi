package com.example.publicwifi.network

import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.example.publicwifi.network.ApiResponse

interface ApiService {
    // 회원가입 API
    @FormUrlEncoded
    @POST("signup/")
    fun registerUser(
        @Field("userid") userid: String,
        @Field("passwd") passwd: String,
        @Field("phone_number") phone_number: String
    ): Call<ApiResponse>

    // 로그인 API
    @FormUrlEncoded
    @POST("login/")
    fun loginUser(
        @Field("userid") userid: String,
        @Field("passwd") passwd: String
    ): Call<ApiResponse>
}