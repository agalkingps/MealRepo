package ru.agalkingps.mealapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import ru.agalkingps.mealapp.login_flow.LoginActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ru.agalkingps.mealapp.services.ServiceLocator.appContext = applicationContext
        applicationContext.startActivity(
            Intent(applicationContext, LoginActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

    }
}
