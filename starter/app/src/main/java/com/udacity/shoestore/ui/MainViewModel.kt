package com.udacity.shoestore.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.models.User

typealias ShoeDatabase = MutableMap<String, MutableList<Shoe>> // (User-)email => Shoes
typealias UserDatabase = MutableMap<String, User> // email => User

class MainViewModel : ViewModel() {

    private val _shoeDatabase: ShoeDatabase = mutableMapOf()
    private val _userDatabase: UserDatabase = mutableMapOf()
    private var _currentUser: User? = null

    private val _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    private var _loggedOut = MutableLiveData<Boolean>(false)
    val loggedOut: LiveData<Boolean>
        get() = _loggedOut

    init {
        _currentUser = null
        _shoeList.value = mutableListOf()
    }

    fun updateShoe(index: Int,
                   name: String,
                   size: Double,
                   company: String,
                   description: String) {

        _shoeList.value?.get(index)?.let {
            it.name = name
            it.size = size
            it.company = company
            it.description = description
        }

        // Sync with "database"
        _shoeDatabase[_currentUser!!.email] = _shoeList.value!!
    }

    fun addShoe(name: String,
                size: Double,
                company: String,
                description: String) {

        shoeList.value?.add(Shoe(name, size, company, description))

        // Sync with "database"
        _shoeDatabase[_currentUser!!.email] = _shoeList.value!!

    }

    fun login(email: String, password: String) : Boolean {

        val user = _userDatabase[email]

        return if (user != null && user.password == password) {
            _currentUser = user
            _shoeList.value = _shoeDatabase[user.email]
            true
        } else {
            false
        }
    }

    fun register(email: String, password: String) : Boolean {

        val existingUser = _userDatabase[email]

        if (existingUser != null) {
            return false
        }

        val newUser = User(email, password)
        _userDatabase[email] = newUser
        _currentUser = newUser

        _shoeDatabase[email] = mutableListOf()
        _shoeList.value = mutableListOf()

        return true
    }

    fun logout() {
        _shoeList.value = mutableListOf()
        _currentUser = null
        _loggedOut.value = true
    }

    fun logoutFinished() {
        _loggedOut.value = false
    }

}