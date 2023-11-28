package ru.agalkingps.mealapp.order_flow

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.agalkingps.mealapp.data.model.User

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userId : Int,
) {
    val context = LocalContext.current
    val viewModel: MealViewModel = hiltViewModel(context as ComponentActivity)

    viewModel.collectOrderFlowToStateList(userId)
    var list = viewModel.orderStateList

    Column (modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    ){
        TitleText(
            text = viewModel.userState.value!!.firstName + " " + viewModel.userState.value!!.lastName
            + " - $%.2f".format(viewModel.totalState.value)
        )

        if (viewModel.orderStateList.value.isEmpty()) {
            CentralText(
                text = stringResource(R.string.under_construction)
            )
            return
        }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
        ) {

            itemsIndexed(list.value) { idx,
                                       order ->
                OrderCard(order,
                    onClick = { }
                )
            }
        }

    }
}
