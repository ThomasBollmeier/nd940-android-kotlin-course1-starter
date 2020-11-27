package com.udacity.shoestore.models

data class User(val email: String, private val password: String) {

    companion object {
        fun readAll() = mutableListOf(
            User("tbollmeier@web.de", "geheim007")
        )
    }

    fun isValid(password: String) = this.password == password

}