package ru.agalkingps.mealapp.order_flow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FloatingActionButton(
    icon: ImageVector,
    containerColor: Color = Color.Red,
    contentColor: Color = Color.White,
    verticalArrangement: Arrangement.Vertical = Arrangement.Bottom,
    horizontalAlign: Alignment.Horizontal = Alignment.End,
    onClick: () -> Unit = {}
    ) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlign,
    )
    {
        LargeFloatingActionButton(
            onClick = {
                  onClick()
            },
            shape = CircleShape,
            containerColor = containerColor,
            contentColor = contentColor,
        ) {
            Icon(
                icon,
                "Large floating action button",
                modifier = Modifier.size(64.dp))
        }
    }

}