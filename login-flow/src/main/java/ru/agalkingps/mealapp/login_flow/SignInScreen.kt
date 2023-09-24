package ru.agalkingps.mealapp.login_flow

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun SignInScreen(
    onSignInDone: () -> Unit
) {
    val onClickSignInButton : () -> Unit = {
        onSignInDone()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Sign In Screen")
        Button(onClick = onClickSignInButton) {
            Text(text = "Sign In")
        }
    }
}

