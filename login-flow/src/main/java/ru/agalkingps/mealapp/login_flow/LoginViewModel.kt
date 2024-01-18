package ru.agalkingps.mealapp.login_flow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import ru.agalkingps.mealapp.data.UserRepository
import ru.agalkingps.mealapp.data.model.User
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor()  : ViewModel() {

    @Inject lateinit var userRepository: UserRepository

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

    fun loginUser(email: String) : User? {
        var user : User? = null
        runBlocking {
            try {
                val deferred = async { userRepository.getUserByEmail(email) }
                user = deferred.await()
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return user;
    }

    fun signInNewUser() : User? {
        var user : User? = null
        runBlocking {
            try {
                val deferred = async { userRepository.getUserByEmail(email) }
                user = deferred.await()
                if (user == null) {
                    user = User(0, firstName, lastName, email, password, 0.0, null)
                    val deferred2 = async { userRepository.addUser(user!!) }
                    val newRowId: Long = deferred2.await()
                    if (newRowId.toInt() == -1) {
                        user = null
                    }
                    else {
                        user!!.id = newRowId.toInt()
                    }
                }
                else {
                    user = null
                }
            } catch (e: Exception) {
                user = null
                e.printStackTrace()
            }
        }
        return user;
    }
}