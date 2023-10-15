package ru.agalkingps.mealapp.login_flow

import android.content.Context
import androidx.annotation.NonNull
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.agalkingps.mealapp.data.model.User
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginViewModel(context: Context)  : ViewModel() {

    val userRepository = ru.agalkingps.mealapp.services.ServiceLocator.getUserRepository(context)
//        userRepository.justTest()

    var password by mutableStateOf("")
    var isPasswordError by mutableStateOf(false)
    var email by mutableStateOf("")
    var isEmailError by mutableStateOf(false)
    var confirmPassword by mutableStateOf("")
    var passwordMismatched by mutableStateOf(false)
    var firstName by mutableStateOf("")
    var isFirstNameError by mutableStateOf(false)
    var lastName by mutableStateOf("")
    var isLastNameError by mutableStateOf(false)

    var loginCompletion by mutableStateOf(false)
    var signInCompletion  by mutableStateOf(false)

    var currentUser : User? = null


    fun verifyPassword(): Boolean {
        return (password.length < 8)
    }

    fun confirmPassword(): Boolean {
        return (password != confirmPassword)
    }

    private val EMAIL_PATTERN: String =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    private val pattern: Pattern = Pattern.compile(EMAIL_PATTERN);

    fun verifyEmail(): Boolean {
        val matcher: Matcher = pattern.matcher(email)
        return !matcher.matches()
    }

    fun verifyFirstName(): Boolean {
        return (firstName.isEmpty())
    }

    fun verifyLastName(): Boolean {
        return (lastName.isEmpty())
    }

    fun loginUser(email: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                currentUser = userRepository.getUserByEmail(email)
                withContext(Dispatchers.Main) {
                    loginCompletion = true
                }
            }
        } catch (e: Exception) {
            currentUser = null
            e.printStackTrace()
        }
    }

    fun signInNewUser() {
        var user : User? = null
        try {
            viewModelScope.launch(Dispatchers.IO) {
                var user : User? = userRepository.getUserByEmail(email)
                if (user == null) {
                    user = User(0, firstName, lastName, email, password, 0.0, null)
                    val newRowId: Long = userRepository.addUser(user!!)
                    currentUser = if (newRowId.toInt() == -1) null else user
                }
                signInCompletion = true
            }
        } catch (e: Exception) {
            currentUser = null
            e.printStackTrace()
        }
    }
}