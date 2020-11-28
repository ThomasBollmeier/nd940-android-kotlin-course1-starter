package com.udacity.shoestore.ui

import android.icu.number.NumberFormatter
import android.icu.number.Precision
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.util.doubleToString
import com.udacity.shoestore.util.stringToDouble
import java.text.NumberFormat
import java.util.*

class ShoeDetailViewModelFactory(
    private val sharedViewModel: MainViewModel,
    private val shoeIdx: Int
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoeDetailViewModel::class.java)) {
            return ShoeDetailViewModel(sharedViewModel, shoeIdx) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}


@RequiresApi(Build.VERSION_CODES.N)
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
                size.value = doubleToString(shoe.size)
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

    @RequiresApi(Build.VERSION_CODES.N)
    fun onSaved() {

        val name = name.value ?: ""
        val size = try {
            stringToDouble(size.value ?: "")
        } catch (e: java.text.ParseException) {
            return
        }
        val company = company.value ?: ""
        val description = description.value ?: ""

        if (shoeIdx != -1) {
            sharedViewModel.updateShoe(
                shoeIdx,
                name,
                size,
                company,
                description
            )
        } else {
            sharedViewModel.addShoe(
                name,
                size,
                company,
                description
            )
        }
        _saved.value = true
    }

    fun onSavedFinished() {
        _saved.value = false
    }

}