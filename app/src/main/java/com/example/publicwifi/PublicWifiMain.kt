package com.example.publicwifi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.publicwifi.navgation.AuthNavGraph
import com.example.publicwifi.navgation.MainNavGraph


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicWifiMain() {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            MainNavGraph(navController = navController)
        }
    }
}