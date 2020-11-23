package com.udacity.shoestore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class MainViewModel : ViewModel() {

    private val _shoeList = MutableLiveData<List<Shoe>>()
    val shoeList : LiveData<List<Shoe>>
        get() = _shoeList

    init {

        _shoeList.value = listOf(
            Shoe("Gel Kayano", 11.5, "Asics", "The classic runner"),
            Shoe("Trail Master", 11.0, "Addidas", "Master your trail runs"),
            Shoe("Air Zoom", 11.0, "Nike", "A good one")
        )

    }

}