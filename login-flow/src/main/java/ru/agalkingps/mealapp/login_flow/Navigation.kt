package ru.agalkingps.mealapp.login_flow

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.agalkingps.mealapp.order_flow.OrderActivity

fun NavGraphBuilder.loginGraph(navController: NavController, context: Context) {
    navigation(startDestination = "loginScreen", route = "loginFlow") {
        composable(route = "loginScreen") {
            LoginScreen(
                onLoginDone = {
                    context.startActivity(Intent(context, OrderActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                    navController.navigate("profileScreen")
                },
                onSignInStart = {
                    navController.navigate("signInScreen")
                }
            )
        }
        composable(route = "signInScreen") {
            SignInScreen(
                onSignInDone = {
                    context.startActivity(Intent(context, OrderActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                    navController.navigate("profileScreen")
                }
            )
        }
    }
}
