package com.example.publicwifi.navgation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.publicwifi.screens.LoginScreen
import com.example.publicwifi.screens.MainScreen


@Composable
fun MainNavGraph(navController: NavHostController) {
    val actions = remember(navController) { AuthActions(navController) }

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") { backStackEntry: NavBackStackEntry ->
            MainScreen()
        }
    }

}


class MainAction(navController: NavHostController) {
//    val openLoginScreen = { from: NavBackStackEntry ->
//        navController.navigate("login")
//    }
//
//    val openRegisterScreen = { from: NavBackStackEntry ->
//        navController.navigate("signUp")
//    }

    val upPress: (from: NavBackStackEntry) -> Unit = { from: NavBackStackEntry ->
        navController.navigateUp()
    }
}