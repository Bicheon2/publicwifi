package com.example.publicwifi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.tooling.preview.Preview
import com.example.publicwifi.data.UserDataStore
import com.example.publicwifi.data.UserDataStoreKey
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val userDataStore = UserDataStore(LocalContext.current)
            val isUserExist =
                userDataStore.findUserInfo(UserDataStoreKey.USER_ID_KEY).collectAsState(initial = "")
            PublicWifiMain()
//            if (isUserExist.value?.isNotEmpty() == true) {
//                PublicWifiMain()
//            } else {
//                PublicWifiAuth()
//            }
        }
    }
}

