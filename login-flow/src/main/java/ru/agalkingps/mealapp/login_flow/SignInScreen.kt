package ru.agalkingps.mealapp.login_flow

import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    onSignInDone: () -> Unit
) {
    val onClickSignInButton : () -> Unit = {
        onSignInDone()
    }
    val context = LocalContext.current
    val viewModel = viewModel { LoginViewModel(context) }

    val invalidCredentials = stringResource(R.string.invalid_credentials)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 16.dp)
    ) {

        Text(
            text = stringResource(R.string.sign_in),
            style = MaterialTheme.typography.displayMedium,

            modifier = Modifier
                .fillMaxHeight(0.3f)
                .padding(top = 100.dp)
        )

        FirstNameTextField()
        LastNameTextField()
        EmailTextField()
        PasswordTextField()
        ConfirmPasswordTextField()

        Button(
            modifier = Modifier
                .padding(top = 88.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            onClick = {
                viewModel.currentUser = viewModel.signInNewUser()
                if (viewModel.currentUser != null) {
                    onSignInDone()
                }
                else {
                    Toast.makeText(context, invalidCredentials, Toast.LENGTH_LONG).show()
                }
            },
            enabled = !viewModel.verifyFirstName() && !viewModel.verifyLastName() &&
                !viewModel.verifyEmail() && !viewModel.verifyPassword() && !viewModel.confirmPassword()
        ) {
            Text(text = stringResource(R.string.sign_in))
        }

    }
}

