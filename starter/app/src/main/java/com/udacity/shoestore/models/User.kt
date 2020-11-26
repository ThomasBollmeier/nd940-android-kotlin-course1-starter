package com.udacity.shoestore.models

data class User(val email: String, private val password: String) {

    fun isValid(password: String) = this.password == password

}