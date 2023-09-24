package ru.agalkingps.mealapp.login_flow

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LoginScreen(
    onLoginDone: () -> Unit,
    onSignInStart: () -> Unit
) {
    val onClickLoginButton : () -> Unit = {
        onLoginDone()
    }
    val onClickSignInButton : () -> Unit = {
        onSignInStart()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Text( text = "Welcome Back",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxHeight(0.4f).padding(top = 100.dp)
         )
//        TextField()
        Button(onClick = onClickLoginButton) {
            Text(text = "Login")
        }
        Button(onClick = onClickSignInButton) {
            Text(text = "Sign In")
        }
    }
}
