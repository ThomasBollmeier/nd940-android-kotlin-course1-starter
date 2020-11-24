package com.udacity.shoestore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class MainViewModel : ViewModel() {

    private val _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList : LiveData<MutableList<Shoe>>
        get() = _shoeList

    init {

        _shoeList.value = mutableListOf(
            Shoe("Gel Kayano", 11.5, "Asics", "The classic runner"),
            Shoe("Trail Master", 11.0, "Addidas", "Master your trail runs"),
            Shoe("Air Zoom", 11.0, "Nike", "A good one")
        )

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

}