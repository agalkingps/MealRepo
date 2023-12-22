package ru.agalkingps.mealapp.order_flow

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import ru.agalkingps.mealapp.data.model.Meal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectMealScreen(
    userId: Int,
    onSelectionDone: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: MealViewModel = hiltViewModel(context as ComponentActivity)

    val lazyMealItems = viewModel.mealPagingDataFlow.collectAsLazyPagingItems()

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
            if (lazyMealItems.loadState.refresh == LoadState.Loading) {
                item {
                    Text(
                        text = "Waiting for items to load from the backend",
                        modifier = Modifier.fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
            items(count = lazyMealItems.itemCount) { index ->
                val meal: Meal? = lazyMealItems[index]
                if (meal != null) {
                    MealCard(meal,
                        viewModel.mealSelectedIds.value.contains(meal.id),
                        onClick = {
                            viewModel.toggleMealSelection(meal.id)
                        }
                    )
                }
            }
            if (lazyMealItems.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
    if (viewModel.mealSelectedCount.value > 0) {
        FloatingActionButton(
            icon = Icons.Filled.ShoppingCart,
            onClick = {
                viewModel.putSelectedMealInShoppingCart()
                onSelectionDone()
            }
        )
    }
}
