package ru.agalkingps.mealapp.login_flow

import android.text.TextUtils
import android.util.Patterns
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
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

    val viewModel = viewModel { LoginViewModel() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize().padding(16.dp, 16.dp)
    ) {

        Text(
            text = stringResource(R.string.meal_app),
            style = typography.displayMedium,

            modifier = Modifier.fillMaxHeight(0.3f).padding(top = 100.dp)
        )
        EmailTextField()
        PasswordTextField()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stringResource(R.string.no_account),
            )
            TextButton(
                onClick = onClickLoginButton
            ) {
                Text(
                    text = stringResource(R.string.sign_in),
                    color = Color.Magenta
                )
            }
        }
        Button(
            modifier = Modifier
                .padding(top = 108.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            onClick = onClickLoginButton,
            enabled = viewModel.email.isNotEmpty() && !viewModel.isEmailError &&
                      viewModel.password.isNotEmpty() &&!viewModel.isPasswordError
        ) {
            Text(text = "Login")
        }
    }
}
