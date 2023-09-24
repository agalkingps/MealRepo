package ru.agalkingps.mealapp.login_flow

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.loginGraph(navController: NavController) {
    navigation(startDestination = "loginScreen", route = "loginFlow") {
        composable(route = "loginScreen") {
            LoginScreen(
                onLoginDone = {
                    navController.navigate("profileScreen")
                },
                onSignInStart = {
                    navController.navigate("signInScreen")
                }
            )
        }
        composable(route = "signInScreen") {
            SignInScreen(
                onSignInDone = {
                    navController.navigate("profileScreen")
                }
            )
        }
    }
}
