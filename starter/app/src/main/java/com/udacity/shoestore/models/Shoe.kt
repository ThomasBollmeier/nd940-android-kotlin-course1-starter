package com.udacity.shoestore.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Shoe(var name: String, var size: Double, var company: String, var description: String,
                val images: List<String> = mutableListOf()) : Parcelable
{
    companion object {
        fun getAll() = mutableListOf(
            Shoe("Gel Kayano", 11.5, "Asics", "The classic runner"),
            Shoe("Trail Master", 11.0, "Adidas", "Master your trail runs"),
            Shoe("Air Zoom", 11.0, "Nike", "A good one")
        )
    }

}