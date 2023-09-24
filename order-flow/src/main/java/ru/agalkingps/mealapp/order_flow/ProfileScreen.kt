package ru.agalkingps.mealapp.order_flow

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun ProfileScreen(
    onOrderStart: () -> Unit
) {
    val onClickOrderButton : () -> Unit = {
        onOrderStart()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Profile Screen")
        Button(onClick = onClickOrderButton) {
            Text(text = "Order")
        }
    }
}
