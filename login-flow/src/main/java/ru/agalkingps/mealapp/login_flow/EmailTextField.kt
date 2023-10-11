package ru.agalkingps.mealapp.login_flow

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField() {
    val viewModel = viewModel { LoginViewModel() }

    OutlinedTextField(
        value = viewModel.email,
        onValueChange = {
            viewModel.email = it
            viewModel.verifyEmail()
        },
        isError = viewModel.isEmailError,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(50),
        label = { Text(stringResource(R.string.email)) },
        placeholder = { Text(stringResource(R.string.type_email)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription="personIcon") },
        trailingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription="emailIcon") },
        supportingText = {
            if (viewModel.isEmailError) {
                Text (
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.email_is_not_valid),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )

}
