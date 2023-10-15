package ru.agalkingps.mealapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import ru.agalkingps.mealapp.data.UserRepositoryInterface
import ru.agalkingps.mealapp.repo.database.UserDatabase
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.agalkingps.mealapp.login_flow.LoginActivity
import ru.agalkingps.mealapp.ui.theme.MealAppTheme
import ru.agalkingps.mealapp.login_flow.loginGraph
import ru.agalkingps.mealapp.order_flow.OrderActivity
import ru.agalkingps.mealapp.order_flow.orderGraph
import ru.agalkingps.mealapp.services.ServiceLocator


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applicationContext.startActivity(
            Intent(applicationContext, LoginActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));


//        val userRepository = ServiceLocator.getUserRepository(this.applicationContext)
//        userRepository.justTest()
/*
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
                        startDestination = "LoginFlow"
                    ) {
                        loginGraph(controller, getApplicationContext())
                        orderGraph(controller, getApplicationContext())
                    }
                }
            }
        }
        */
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MealAppTheme {
        Greeting("Android")
    }
}

