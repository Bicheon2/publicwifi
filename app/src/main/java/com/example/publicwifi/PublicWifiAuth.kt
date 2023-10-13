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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicWifiAuth() {
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            AuthNavGraph(navController = navController)
        }
    }
}