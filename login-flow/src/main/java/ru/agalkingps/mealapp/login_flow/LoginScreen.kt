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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    onLoginDone: () -> Unit,
    onSignInStart: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = viewModel { LoginViewModel(context) }

    val invalidCredentials = stringResource(R.string.invalid_credentials)

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
                onClick = onSignInStart
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
            onClick = {
                viewModel.loginUser(viewModel.email)
            },
            enabled = !viewModel.verifyEmail() && !viewModel.verifyPassword()
        ) {
            Text(text = stringResource(R.string.login))
        }
        if (viewModel.loginCompletion) {
            viewModel.loginCompletion = false
            if (viewModel.currentUser == null || viewModel.currentUser!!.password != viewModel.password) {
                Toast.makeText(context, invalidCredentials, Toast.LENGTH_LONG).show()
            }
            else{
                onLoginDone()
            }
        }
    }
}
