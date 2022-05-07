package com.bws.musclefood.itemcategory.basket

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



data class BasketModel(
    val deleteDate: String,
    val number: String,
    val clientName: String,
    val value: String,
    val status: String
)