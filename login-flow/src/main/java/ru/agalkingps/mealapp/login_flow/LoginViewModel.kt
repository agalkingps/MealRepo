package ru.agalkingps.mealapp.login_flow

import android.text.TextUtils
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginViewModel  : ViewModel() {

    var password by mutableStateOf("")
    var isPasswordError by mutableStateOf(false)
    var email by mutableStateOf("")
    var isEmailError by mutableStateOf(false)
    var confirmPassword by mutableStateOf("")
    var passwordMismatched by mutableStateOf(false)

    fun verifyPassword() {
        isPasswordError = (password.length < 8)
    }

    fun confirmPassword() {
        passwordMismatched = (!isPasswordError && password != confirmPassword)
    }


    private val EMAIL_PATTERN : String = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    private val pattern : Pattern = Pattern.compile(EMAIL_PATTERN);

    fun verifyEmail() {
        val matcher : Matcher = pattern.matcher(email)
        isEmailError = !matcher.matches()
    }


}
