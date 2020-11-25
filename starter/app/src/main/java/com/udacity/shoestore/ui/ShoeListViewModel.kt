package com.udacity.shoestore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShoeListViewModel: ViewModel() {

    private var _addClicked = MutableLiveData<Boolean>(false)
    val addClicked: LiveData<Boolean>
        get() = _addClicked

    fun onAddShoeClick() {
        _addClicked.value = true
    }

    fun onAddShoeClickFinshed() {
        _addClicked.value = false
    }

}