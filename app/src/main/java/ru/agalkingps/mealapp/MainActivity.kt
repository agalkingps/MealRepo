package ru.agalkingps.mealapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.agalkingps.mealapp.data.UserRepositoryInterface
import ru.agalkingps.mealapp.login_flow.LoginActivity
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applicationContext.startActivity(
            Intent(applicationContext, LoginActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
