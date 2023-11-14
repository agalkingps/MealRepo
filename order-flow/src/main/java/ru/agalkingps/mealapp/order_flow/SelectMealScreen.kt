package ru.agalkingps.mealapp.order_flow

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectMealScreen(
    onSelectionDone: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: MealViewModel = viewModel(context as ComponentActivity)

    var list = viewModel.mealStateList

    Column {
        TitleText(
            text = stringResource(R.string.make_choice)
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {

            itemsIndexed(list.value) { idx, //row -> MealListItem(row)}
                                       meal ->
                MealCard(meal,
                    onClick = {
                        viewModel.toggleMealSelection(idx)
                    }
                )
            }
        }
    }
    if (viewModel.mealSelectedCount.value > 0) {
        FloatingActionButton(
            icon = Icons.Filled.Add,
            onClick = {
                viewModel.putSelectedMealInShoppingCart()
                onSelectionDone()
            }
        )
    }
}
