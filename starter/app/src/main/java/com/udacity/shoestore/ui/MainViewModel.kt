package com.udacity.shoestore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.models.User

class MainViewModel : ViewModel() {

    private val _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList : LiveData<MutableList<Shoe>>
        get() = _shoeList

    private val _currentUser = MutableLiveData<User>()
    val currentUser : LiveData<User>
        get() = _currentUser

    private var _loggedOut = MutableLiveData<Boolean>(false)
    val loggedOut: LiveData<Boolean>
        get() = _loggedOut

    init {

        _shoeList.value = Shoe.readAll()

        _currentUser.value = null

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
    }

    fun addShoe(name: String,
                size: Double,
                company: String,
                description: String) {

        _shoeList.value?.add(Shoe(name, size, company, description))
    }

    fun login(email: String, password: String) {
        _currentUser.value = User(email, password)
    }

    fun register(email: String, password: String) {
        _currentUser.value = User(email, password)
    }

    fun logout() {
        _loggedOut.value = true
        _currentUser.value = null
    }

    fun logoutFinished() {
        _loggedOut.value = false
    }

}