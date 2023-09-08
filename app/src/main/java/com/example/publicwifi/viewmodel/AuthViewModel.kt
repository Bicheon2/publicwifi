package com.example.publicwifi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.publicwifi.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.publicwifi.network.ApiResponse

class AuthViewModel : ViewModel() {

    // 회원가입 함수
    fun registerUser(userid: String, password: String, phone_number: String) {
        val call = RetrofitInstance.apiService.registerUser(userid, password, phone_number)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    // 회원가입 성공 처리
                    val apiResponse = response.body()
                    // UI 업데이트 및 메시지 표시
                } else {
                    // 회원가입 실패 처리
                    // 오류 메시지 표시 등 필요한 작업 수행
                    handleError(response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // 네트워크 오류 등 실패 처리
                handleError(t.message)
                // 오류 메시지 표시 등 필요한 작업 수행
            }
        })
    }

    // 로그인 함수
    fun loginUser(userid: String, password: String) {
        val call = RetrofitInstance.apiService.loginUser(userid, password)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    // 로그인 성공 처리
                    val apiResponse = response.body()
                    // UI 업데이트 및 메시지 표시
                } else {
                    // 로그인 실패 처리
                    // 오류 메시지 표시 등 필요한 작업 수행
                    handleError(response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // 네트워크 오류 등 실패 처리
                handleError(t.message)
                // 오류 메시지 표시 등 필요한 작업 수행
            }
        })
    }
}