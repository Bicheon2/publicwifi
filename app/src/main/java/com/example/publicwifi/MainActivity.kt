package com.example.publicwifi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview
import com.example.publicwifi.api.RetrofitHelper
import com.example.publicwifi.data.UserDataStore
import com.example.publicwifi.data.UserDataStoreKey
import com.example.publicwifi.share.ShareApplication
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val api = RetrofitHelper.getWifiService()
    private val connectedWifiIP = ShareApplication().connectedWifiIP


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val userDataStore = UserDataStore(LocalContext.current)
            val isUserExist =
                userDataStore.findUserInfo(UserDataStoreKey.USER_ID_KEY).collectAsState(initial = "")
            if (isUserExist.value?.isNotEmpty() == true) {
                PublicWifiMain()
            } else {
                PublicWifiAuth()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivityy", "onDestroy")
    }
}

