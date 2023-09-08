package com.example.publicwifi.navgation

import androidx.compose.runtime.Composable
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.publicwifi.screens.LoginScreen
import com.example.publicwifi.screens.SignUpScreen


@Composable
fun NavGraph(
    navController: NavHostController,
) {
    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { backStackEntry: NavBackStackEntry ->
            LoginScreen(
                openSignUpScreen = {
                    actions.openRegisterScreen(
                        backStackEntry
                    )
                },
            )
        }
        composable("signUp") { backStackEntry: NavBackStackEntry ->
            SignUpScreen(
                openLoginScreen = {
                    actions.upPress(
                        backStackEntry
                    )
                },
            )
        }
    }

}

class MainActions(navController: NavHostController) {
    val openLoginScreen = { from: NavBackStackEntry ->
        navController.navigate("login")
    }

    val openRegisterScreen = { from: NavBackStackEntry ->
        navController.navigate("signUp")
    }

    val upPress: (from: NavBackStackEntry) -> Unit = { from: NavBackStackEntry ->
        navController.navigateUp()
    }
}