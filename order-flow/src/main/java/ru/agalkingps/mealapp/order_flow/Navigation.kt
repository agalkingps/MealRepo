package ru.agalkingps.mealapp.order_flow

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.orderGraph(navController: NavController, context: Context) {
    navigation(startDestination = "profileScreen", route = "orderFlow") {
        composable(route = "profileScreen") {
            ProfileScreen(
                onOrderStart = {
                    navController.navigate("orderScreen")
                }
            )
        }
        composable(route = "orderScreen") {
            OrderScreen(
                onOrderDone = {
                    navController.popBackStack()
                 }
            )
        }
    }
}
