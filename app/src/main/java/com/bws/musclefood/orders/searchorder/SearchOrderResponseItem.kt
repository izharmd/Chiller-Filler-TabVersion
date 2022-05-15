package com.bws.musclefood.orders.searchorder

data class SearchOrderResponseItem(
    val OrderAmount: String,
    val OrderDate: String,
    val OrderID: String,
    val OrderItemList: ArrayList<OrderItemList>,
    val OrderNumber: String,
    val OrderStatus: String,
    val StoreID: String,
    val StoreName: String
)