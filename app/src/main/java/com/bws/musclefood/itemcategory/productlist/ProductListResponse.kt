package com.bws.musclefood.itemcategory.productlist

class ProductListResponse : ArrayList<ProductListResponseItem>()


data class ProductListResponseItem(
    val CategoryID: String,
    val CategoryName: String,
    val ProductID: String,
    val ProductImage: String,
    val ProductName: String,
    val ProductPrice: String,
    val ProductPriceFormatted: String,
    val ProductSize: String,
    val SubCategoryID: String,
    val SubCategoryName: String,
    val FavoriteFlag: String
)