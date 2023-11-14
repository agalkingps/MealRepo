package ru.agalkingps.mealapp.order_flow

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfileScreen(
    userId : Int
) {
    val context = LocalContext.current
    val viewModel: MealViewModel = viewModel(context as ComponentActivity)
    Column {
        TitleText(
            text = "Profile Screen $userId"
        )
        CentralText(
            text = stringResource(R.string.under_construction),
        )
    }
}
