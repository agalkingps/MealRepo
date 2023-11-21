package ru.agalkingps.mealapp.order_flow

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileScreen(
    userId : Int,
) {
    val context = LocalContext.current
    val viewModel: MealViewModel = hiltViewModel(context as ComponentActivity)

    Column {
        TitleText(
            text = "Profile Screen $userId"
        )
        CentralText(
            text = stringResource(R.string.under_construction),
        )
    }
}
