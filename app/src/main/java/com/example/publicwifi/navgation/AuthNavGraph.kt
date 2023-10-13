package com.example.publicwifi.navgation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.publicwifi.screens.LoginScreen
import com.example.publicwifi.screens.SignUpScreen


@Composable
fun AuthNavGraph(
    navController: NavHostController,
) {
    val actions = remember(navController) { AuthActions(navController) }
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

class AuthActions(navController: NavHostController) {
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