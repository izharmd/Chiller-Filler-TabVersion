package com.bws.musclefood.orders.reorder

data class ReorderResponseItem(
    val OrderAmount: String,
    val OrderDate: String,
    val OrderID: String,
    val OrderItemList: List<OrderItem>,
    val OrderNumber: String,
    val OrderStatus: String,
    val StoreID: String,
    val StoreName: String
)