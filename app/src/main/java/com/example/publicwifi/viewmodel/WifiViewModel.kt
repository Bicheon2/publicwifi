package com.example.publicwifi.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.publicwifi.api.RetrofitHelper
import com.example.publicwifi.api.dto.WifiDto
import com.example.publicwifi.observer.AppLifecycleObserver
import com.example.publicwifi.share.ShareApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WifiViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private val api = RetrofitHelper.getWifiService()
    private val _wifiList: MutableStateFlow<List<WifiDto?>> = MutableStateFlow<List<WifiDto?>>(
        listOf()
    )
    val wifiList: MutableStateFlow<List<WifiDto?>>
        get() = _wifiList

    fun getWifiList() {
        api.getWifi().enqueue(object : retrofit2.Callback<List<WifiDto?>> {
            override fun onResponse(
                call: Call<List<WifiDto?>>,
                response: Response<List<WifiDto?>>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (response.code() == 200) {
                        Log.d("wifiViewModel", "onResponse1: ${result}")
                        _wifiList.value = result!!
                    }
                    Log.d("wifiViewModel", "onResponse: $result")
                }
            }

            override fun onFailure(call: Call<List<WifiDto?>>, t: Throwable) {
                Log.d("wifiViewModel", "onFailure Api: ${t.message}")
            }
        })
    }
}