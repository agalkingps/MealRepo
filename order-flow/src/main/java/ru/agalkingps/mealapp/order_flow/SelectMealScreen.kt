package ru.agalkingps.mealapp.order_flow

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectMealScreen(
    userId: Int,
    onSelectionDone: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: MealViewModel = hiltViewModel(context as ComponentActivity)

    LaunchedEffect(viewModel) {
        viewModel.collectMealFlowToStateList()
    }

    viewModel.fetchUserById(userId)

    Column (modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    ){
        TitleText(
            text = stringResource(R.string.make_choice)
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
        ) {

            itemsIndexed(viewModel.mealStateList.value) { idx,
                                                          meal ->
                MealCard(meal,
                    viewModel.mealSelectedIds.value.contains(meal.id),
                    onClick = {
                        viewModel.toggleMealSelection(meal.id)
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
