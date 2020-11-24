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
    val size = MutableLiveData<String>()
    val company = MutableLiveData<String>()
    val description = MutableLiveData<String>()

    private val _canceled = MutableLiveData<Boolean>()
    val canceled: LiveData<Boolean>
        get() = _canceled

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean>
        get() = _saved

    init {
        if (shoeIdx != -1) {
            sharedViewModel.shoeList.value?.get(shoeIdx)?.let { shoe ->
                name.value = shoe.name
                size.value = String.format("%.1f", shoe.size)
                company.value = shoe.company
                description.value = shoe.description
            }
        } else {
            // new shoe
            name.value = ""
            size.value = ""
            company.value = ""
            description.value = ""
        }
        _canceled.value = false
        _saved.value = false
    }

    fun onCanceled() {
        _canceled.value = true
    }

    fun onCanceledFinished() {
        _canceled.value = false
    }

    fun onSaved() {

        val shoeSize = try {
            size.value?.toDouble() ?: 0.0
        } catch (e: NumberFormatException) {
            0.0
        }

        if (shoeIdx != -1) {

            sharedViewModel.updateShoe(
                shoeIdx,
                name.value ?: "",
                shoeSize,
                company.value ?: "",
                description.value ?: ""
            )
        } else {

            sharedViewModel.addShoe(
                name.value ?: "",
                shoeSize,
                company.value ?: "",
                description.value ?: ""
            )

        }
        _saved.value = true
    }

    fun onSavedFinished() {
        _saved.value = false
    }
}