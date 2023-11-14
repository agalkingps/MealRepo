package ru.agalkingps.mealapp.order_flow

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(
    userId: Int,
    onGotoPay: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: MealViewModel = viewModel(context as ComponentActivity)

    var list = viewModel.orderedMealStateList

    Column {
        TitleText(
            text = stringResource(R.string.shopping_cart) + " - $%.2f".format(viewModel.calcOrderedMealCoast()),
        )
        if (list.value.isEmpty()) {
            CentralText(
                text = stringResource(R.string.make_choice)
            )
            return
        }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {

            itemsIndexed(list.value) { idx, //row -> MealListItem(row)}
                                       meal ->
                MealCard(meal,
                    onClick = {
                        viewModel.toggleOrderedMealSelection(idx)
                    }
                )
            }
        }
    }
    if (viewModel.orderedMealSelectedCount.value > 0) {
        FloatingActionButton(
            icon = Icons.Filled.Delete,
            containerColor = Color.Cyan,
            horizontalAlign = Alignment.CenterHorizontally,
            onClick = {
                viewModel.removeSelectedOrderedMeal()
            }
        )
    }
    FloatingActionButton(
        icon = Icons.Filled.MonetizationOn,
        onClick = {
            viewModel.orderSelectedMeal()
            onGotoPay()
        }
    )
}
