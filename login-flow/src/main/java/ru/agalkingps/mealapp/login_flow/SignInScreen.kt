package ru.agalkingps.mealapp.login_flow

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.agalkingps.mealapp.data.UserRepositoryInterface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    onSignInDone: (Int) -> Unit
) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = hiltViewModel(context as ComponentActivity)

    val invalidCredentials = stringResource(R.string.invalid_credentials)
    val accountExists = stringResource(R.string.account_exists)

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
                .fillMaxHeight(0.25f)
                .padding(top = 75.dp)
        )

        FirstNameTextField()
        LastNameTextField()
        EmailTextField()
        PasswordTextField()
        ConfirmPasswordTextField()

        Button(
            modifier = Modifier
                .padding(top = 70.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            onClick = {
                val user = viewModel.signInNewUser()
                if (user != null) {
                    onSignInDone(user.id)
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

