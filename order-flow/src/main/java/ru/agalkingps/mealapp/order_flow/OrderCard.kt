package ru.agalkingps.mealapp.order_flow

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.agalkingps.mealapp.data.model.Order
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCard (
    order: Order,
    onClick: () -> Unit,
) {
    val pattern = "yyyy-MM-dd HH:mm";
    var simpleDateFormat = SimpleDateFormat(pattern);

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
            Text(
                text = simpleDateFormat.format(order.timestamp),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Left
            )
            Text(
                text = " - $%.2f".format(order.total),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

        }
    }
}
