package ru.agalkingps.mealapp.order_flow

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.agalkingps.mealapp.order_flow.ui.theme.MealAppTheme

class OrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val controller = rememberNavController()

                    NavHost(
                        navController = controller,
                        startDestination = "orderFlow"
                    ) {
                        orderGraph(controller, applicationContext)
                    }
                }
            }
        }
    }
    override fun onBackPressed() {
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
