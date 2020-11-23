package com.udacity.shoestore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

data class ShoeItem(val index: Int, val shoe: Shoe)

class ItemClickedEvent(val index: Int)

class ShoeListViewModel: ViewModel() {

    private var _itemClicked = MutableLiveData<ItemClickedEvent>(null)
    val itemClicked: LiveData<ItemClickedEvent>
        get() = _itemClicked

    fun onItemClicked(index: Int) {
        _itemClicked.value = ItemClickedEvent(index)
    }

    fun onItemClickFinished() {
        _itemClicked.value = null
    }

}