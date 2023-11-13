package ru.agalkingps.mealapp.order_flow

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SetMeal
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    object ProfileScreen : Destinations(
        route = "profile_screen",
        title = "Profile",
        icon = Icons.Outlined.Person
    )

    object SetMealScreen : Destinations(
        route = "set_meal_screen",
        title = "Meal",
        icon = Icons.Outlined.SetMeal
    )

    object ShoppingCartScreen : Destinations(
        route = "shopping_cart_screen",
        title = "Shopping Cart",
        icon = Icons.Outlined.ShoppingCart
    )

}

@Composable
fun NavigationGraph(navController: NavHostController, userId: Int) {

    NavHost(navController, startDestination = Destinations.SetMealScreen.route) {
        composable(Destinations.ProfileScreen.route) {
            ProfileScreen(userId)
        }
        composable(Destinations.SetMealScreen.route) {
            SetMealScreen(
                onSelectionDone = {
                    navController.navigate(Destinations.ShoppingCartScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(Destinations.ShoppingCartScreen.route) {
            ShoppingCartScreen(userId,
                onGotoPay = {
                    navController.navigate(Destinations.ProfileScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

