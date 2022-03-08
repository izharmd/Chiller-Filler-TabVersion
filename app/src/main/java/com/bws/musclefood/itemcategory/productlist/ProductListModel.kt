package com.bws.musclefood.itemcategory.productlist

data class ProductListModel(
    val productImage: Int, val productName: String, val productPrice: String,
    val productDiscountPrice: String,
    val deliveryTime: String, val brandName: String,
    val quentity:String,val offer:String,
    val ratingPoint:String,val ratingStr:String,
    val outOfStock:String
)