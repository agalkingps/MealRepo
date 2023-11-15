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

    object SelectMealScreen : Destinations(
        route = "select_meal_screen",
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

    NavHost(navController, startDestination = Destinations.SelectMealScreen.route) {
        composable(Destinations.ProfileScreen.route) {
            ProfileScreen(userId)
        }
        composable(Destinations.SelectMealScreen.route) {
            SelectMealScreen(
                onSelectionDone = {
                    navController.switchTabs(Destinations.ShoppingCartScreen.route)
                }
            )
        }
        composable(Destinations.ShoppingCartScreen.route) {
            ShoppingCartScreen(userId,
                onGotoPay = {
                    navController.switchTabs(Destinations.ProfileScreen.route)
                }
            )
        }
    }
}

fun NavHostController.switchTabs(route: String) {
    navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }

        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true

        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}


