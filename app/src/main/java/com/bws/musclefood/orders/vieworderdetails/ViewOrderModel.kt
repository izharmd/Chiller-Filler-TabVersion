package com.bws.musclefood.orders.vieworderdetails

data class ViewOrderModel(
    val image: Int,
    val pName: String,
    val quantity: String,
    val price: String,
    val netPrice: String,
    val youSaved: String
)
