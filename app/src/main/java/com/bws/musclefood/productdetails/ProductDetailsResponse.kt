package com.bws.musclefood.productdetails

class ProductDetailsResponse : ArrayList<ProductDetailsResponseItem>()


data class ProductDetailsResponseItem(
    val CategoryID: String,
    val CategoryName: String,
    val Ingredients: String,
    val Nutritionals: String,
    val ProductID: String,
    val ProductImage: String,
    val ProductName: String,
    val ProductPrice: String,
    val ProductPriceFormatted: String,
    val ProductSize: String,
    val ShelfLife: String,
    val StorageInstructions: String,
    val SubCategoryID: String,
    val SubCategoryName: String
)