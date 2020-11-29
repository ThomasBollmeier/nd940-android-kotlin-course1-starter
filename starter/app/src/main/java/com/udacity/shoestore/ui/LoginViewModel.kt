package com.udacity.shoestore.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

data class LoginResult(val success: Boolean, val error: String = "")

class LoginViewModelFactory(
    private val sharedViewModel: MainViewModel,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(sharedViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

class LoginViewModel(private val sharedViewModel: MainViewModel) : ViewModel() {

    val email = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

    private var _loginResult = MutableLiveData<LoginResult>(null)
    val loginResult: LiveData<LoginResult>
        get() = _loginResult

    fun login() {

        val email = email.value ?: ""
        val password = password.value ?: ""

        val validationResult = validate(email, password)
        if (!validationResult.success) {
            _loginResult.value = validationResult
            return
        }

        if (sharedViewModel.login(email, password)) {
            _loginResult.value = LoginResult(true)
        } else {
            _loginResult.value = LoginResult(false, "email or password invalid")
        }
    }

    fun register() {

        val email = email.value ?: ""
        val password = password.value ?: ""

        val validationResult = validate(email, password)
        if (!validationResult.success) {
            _loginResult.value = validationResult
            return
        }

        if (sharedViewModel.register(email, password)) {
            _loginResult.value = LoginResult(true)
        } else {
            _loginResult.value = LoginResult(false, "User $email exists")
        }
    }

    fun loginResultFinished() {
        _loginResult.value = null
    }

    private fun validate(email: String, password: String) : LoginResult {
        if (email.isEmpty()) {
            return LoginResult(false, "email must not be empty")
        }
        if (!email.contains('@')) {
            return LoginResult(false, "email has invalid format")
        }
        if (password.length < 6) {
            return LoginResult(false, "password must be at least 6 characters long")
        }
        return LoginResult(true)
    }

}