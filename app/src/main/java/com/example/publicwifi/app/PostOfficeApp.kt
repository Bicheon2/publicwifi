package com.example.publicwifi.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.publicwifi.navigation.PostOfficeAppRouter
import com.example.publicwifi.navigation.Screen
import com.example.publicwifi.screens.LoginScreen
import com.example.publicwifi.screens.SignUpScreen
import com.example.publicwifi.screens.TermsAndConditionsScreen
import com.example.publicwifi.viewmodel.AuthViewModel

@Composable
fun PostOfficeApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Crossfade(targetState = PostOfficeAppRouter.currentScreen) { currentState ->
            when (currentState.value) {
                is Screen.SignUpScreen -> {
                    SignUpScreen(viewModel = AuthViewModel())
                }

                is Screen.TermsAndConditionsScreen -> {
                    TermsAndConditionsScreen()
                }

                is Screen.LoginScreen -> {
                    LoginScreen()
                }
            }
        }
    }
}
