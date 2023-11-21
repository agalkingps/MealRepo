package ru.agalkingps.mealapp.login_flow

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstNameTextField() {

    val context = LocalContext.current
    val viewModel: LoginViewModel = hiltViewModel(context as ComponentActivity)

    OutlinedTextField(
        value = viewModel.firstName,
        onValueChange = {
            viewModel.firstName = it
            viewModel.isFirstNameError = viewModel.verifyFirstName()
        },
        isError = viewModel.isFirstNameError,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(50),
        label = { Text(stringResource(R.string.first_name)) },
        placeholder = { Text(stringResource(R.string.first_name)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription="personIcon") },
        supportingText = {
            if (viewModel.isFirstNameError) {
                Text (
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.can_not_be_empty),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )

}