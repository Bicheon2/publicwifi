package com.example.publicwifi.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.publicwifi.api.RetrofitHelper
import com.example.publicwifi.api.dto.LoginDtoParams
import com.example.publicwifi.api.dto.SignUpDtoParams
import com.example.publicwifi.data.UserDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(application: Application): AndroidViewModel(application) {
    private var _userid by mutableStateOf("")
    val userid: String
        get() = _userid
    private var _password by mutableStateOf("")
    val password: String
        get() = _password
    private var _phoneNumber by mutableStateOf("")
    val phoneNumber: String
        get() = _phoneNumber

    fun setUserId(userid: String) {
        _userid = userid
    }
    fun setPassword(password: String) {
        _password = password
    }
    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber = phoneNumber
    }

    // 로컬 디비
    private val userDataStore = UserDataStore(application.applicationContext)
    
    // api
    private val api = RetrofitHelper.getAuthService()
    
    // 코루틴
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun requestLoginApi(
        userid: String,
        password: String,
    ) {
        api.login(LoginDtoParams(userid, password)).enqueue(object : retrofit2.Callback<String?> {
            override fun onResponse(call: retrofit2.Call<String?>, response: retrofit2.Response<String?>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("authViewModel", "onResponse: $result")
                    Log.d("authViewModel","로그인 성공")
                    // Todo: 회원 정보 데이터베이스 저장
                    coroutineScope.launch(Dispatchers.IO) {
                        userDataStore.saveUserData(userid, "010-1234-5678")
                    }
                }else {
                    Log.d("authViewModel", "onResponse: ${response.errorBody()}")
                }
            }
            override fun onFailure(call: retrofit2.Call<String?>, t: Throwable) {
                Log.d("authViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun requestSignUpApi(
        userid: String,
        password: String,
        phoneNumber: String,
    ) {
        api.signUp(SignUpDtoParams(userid, password, phoneNumber)).enqueue(object : retrofit2.Callback<String?> {
            override fun onResponse(
                call: retrofit2.Call<String?>,
                response: retrofit2.Response<String?>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("authViewModel", "onResponse: $result")
                    Log.d("authViewModel", "회원가입 성공")
                    // Todo: 회원 정보 데이터베이스 저장
                    coroutineScope.launch(Dispatchers.IO) {
                        userDataStore.saveUserData(userid, phoneNumber)
                    }
                }else {
                    Log.d("authViewModel", "onResponse: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.d("authViewModel", "onFailure: ${t.message}")
            }
        })
    }
}