package ru.agalkingps.mealapp.login_flow

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField() {

    val context = LocalContext.current
    val viewModel: LoginViewModel = hiltViewModel(context as ComponentActivity)

    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.design_ic_visibility_x)
    else
        painterResource(id = R.drawable.design_ic_visibility_off_x)

    OutlinedTextField(
        value = viewModel.password,
        onValueChange = {
                viewModel.password = it
                viewModel.isPasswordError = viewModel.verifyPassword()
        },
        isError = viewModel.isPasswordError,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(50),
        label = { Text(stringResource(R.string.password)) },
        placeholder = { Text(stringResource(R.string.type_password)) },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    painter = icon,
                    contentDescription = "Visibility Icon"
                )
            }
        },
        supportingText = {
            if (viewModel.isPasswordError) {
                Text (
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.password_is_not_valid),
                    color = MaterialTheme.colorScheme.error
                )
            }
       },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if (passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}
