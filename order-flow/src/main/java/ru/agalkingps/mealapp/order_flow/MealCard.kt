package ru.agalkingps.mealapp.order_flow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import ru.agalkingps.mealapp.data.model.Meal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealCard(
    meal: Meal,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        onClick = {
            onClick()
        },
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row {
            MealImage(meal)
            Column(
                modifier = Modifier
                    .weight(100f)
                    .align(Alignment.CenterVertically)

            ) {
                Text(
                    text = meal.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "$${meal.price}" + if (meal.count > 0) " x ${meal.count}" else "",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            if (selected/*meal.isSelected*/) {
                Icon(
                    modifier = Modifier
                        .weight(10f)
                        .background(Color.White, CircleShape),
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = Color.Red,
                )
            }
        }
    }
}


@Composable
fun MealImage(meal: Meal) {
    Image(
        painter = painterResource(id = meal.imageId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}
