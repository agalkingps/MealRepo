package ru.agalkingps.mealapp.order_flow

import android.content.DialogInterface
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(
    userId: Int,
    onGotoPay: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: MealViewModel = hiltViewModel(context as ComponentActivity)

    Column (modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    ){
        TitleText(
            text = stringResource(R.string.shopping_cart) + " - $%.2f".format(viewModel.calcOrderedMealCoast()),
        )
        if (viewModel.orderedMealStateList.value.isEmpty()) {
            CentralText(
                text = stringResource(R.string.make_choice)
            )
            return
        }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
        ) {

            itemsIndexed(viewModel.orderedMealStateList.value) { idx, //row -> MealListItem(row)}
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
            val builder = android.app.AlertDialog.Builder(context)
            builder.setMessage(R.string.confirm_payment)
                .setTitle(R.string.confirm)
                .setPositiveButton("Yes") { _, _: Int -> run { viewModel.orderSelectedMeal(userId,
                    onGotoPay) } }
                .setNegativeButton("Cancel") { _, _: Int -> }
            //Creating dialog box
            val alert = builder.create();
            alert.show();
        }
    )
}

