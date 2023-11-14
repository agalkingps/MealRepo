package ru.agalkingps.mealapp.order_flow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CentralText(
    text: String,
    color: Color = Color.Black
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = color,
            textAlign = TextAlign.Center,
        )
    }
}