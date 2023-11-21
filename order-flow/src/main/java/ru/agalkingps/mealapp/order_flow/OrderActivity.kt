package ru.agalkingps.mealapp.order_flow

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.agalkingps.mealapp.order_flow.ui.theme.MealAppTheme

@AndroidEntryPoint
class OrderActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId: Int =  intent.getIntExtra("UserId", -1)
        setContent {
            MealAppTheme {
                MakeGUI(userId)
             }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MakeGUI(userId: Int) {
        val viewModel: MealViewModel = hiltViewModel()

        val navController: NavHostController = rememberNavController()
        val currentRoute = navController
            .currentBackStackEntryFlow
            .collectAsState(initial = navController.currentBackStackEntry)

        var buttonsVisible = remember { mutableStateOf(true) }

        Scaffold(
            bottomBar = {
                BottomBar(
                    navController = navController,
                    state = buttonsVisible,
                    modifier = Modifier
                )
            },
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                NavigationGraph(navController = navController, userId)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.dialog_message)
               .setTitle(R.string.dialog_title)
               .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int -> this.finishAffinity(); }
            .setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int -> }
        //Creating dialog box
        val alert = builder.create();
        alert.show();
    }
}
