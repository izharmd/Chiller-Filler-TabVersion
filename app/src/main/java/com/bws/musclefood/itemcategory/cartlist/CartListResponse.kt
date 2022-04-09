package com.bws.musclefood.itemcategory.cartlist

class CartListResponse : ArrayList<CartListResponseItem>()


data class CartListResponseItem(
    val CartItemID: String,
    val FavoriteFlag: String,
    val FormattedPrice: String,
    val FormattedProductTotalPrice: String,
    val Price: String,
    val ProductID: String,
    val ProductImageName: String,
    val ProductName: String,
    val ProductSize: String,
    val Quantity: String
)