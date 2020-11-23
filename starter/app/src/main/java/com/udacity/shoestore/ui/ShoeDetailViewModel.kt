package com.udacity.shoestore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.shoestore.models.Shoe

class ShoeDetailViewModelFactory(
    private val sharedViewModel: MainViewModel,
    private val shoeIdx: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoeDetailViewModel::class.java)) {
            return ShoeDetailViewModel(sharedViewModel, shoeIdx) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}


class ShoeDetailViewModel(
    private val sharedViewModel: MainViewModel,
    private val shoeIdx: Int
) : ViewModel() {

    val name = MutableLiveData<String>()

    init {
        if (shoeIdx != -1) {
            sharedViewModel.shoeList?.value?.get(shoeIdx)?.let { shoe ->
                name.value = shoe.name
            }
        } else {
            // new shoe
            name.value = ""
        }
    }

}