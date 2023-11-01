package ru.agalkingps.mealapp.order_flow

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.agalkingps.mealapp.data.model.Meal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetMealScreen() {
    val context = LocalContext.current
    val viewModel = viewModel { OrderViewModel(context) }

    var list = viewModel.mealStateList

    Column {
        Text(
            text = stringResource(R.string.make_choice),
            style = typography.headlineLarge,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {

            itemsIndexed(list.value) { idx, //row -> MealListItem(row)}
                                       row ->
                Card(
                    onClick = {
                        viewModel.toggleMealSelection(idx)
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
                        MealImage(row)
                        Column(
                            modifier = Modifier
                                .weight(100f)
                                .align(Alignment.CenterVertically)

                        ) {
                            Text(text = row.title, style = typography.headlineSmall)
                            Text(text = "$${row.price}", style = typography.bodyMedium)
                        }
                        if (row.isSelected) {
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
        }
    }
    if (viewModel.mealSelectedCount.value > 0) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
        )
        {
            LargeFloatingActionButton(
                onClick = {
                    Toast.makeText(context, "FloatingActionButton Clicked", Toast.LENGTH_LONG).show()
                },
                shape = CircleShape,
                containerColor = Color.Red,
                contentColor = Color.White,
            ) {
                Icon(Icons.Filled.Add, "Large floating action button")
            }
        }
    }
}

@Composable
private fun MealImage(meal: Meal) {
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
