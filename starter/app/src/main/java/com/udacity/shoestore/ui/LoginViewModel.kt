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

    private var _email = MutableLiveData<String>("")
    val email: LiveData<String>
        get() = _email

    private var _password = MutableLiveData<String>("")
    val password: LiveData<String>
        get() = _password

    private var _loginResult = MutableLiveData<LoginResult>(null)
    val loginResult: LiveData<LoginResult>
        get() = _loginResult

    fun login() {
        if (sharedViewModel.login(email.value ?: "", password.value ?: "")) {
            _loginResult.value = LoginResult(true)
        }
    }

    fun register() {
        val email = email.value ?: ""
        val password = password.value ?: ""
        if (sharedViewModel.register(email, password)) {
            _loginResult.value = LoginResult(true)
        } else {
            _loginResult.value = LoginResult(false, "User $email exists")
        }
    }

    fun loginResultFinished() {
        _loginResult.value = null
    }

}