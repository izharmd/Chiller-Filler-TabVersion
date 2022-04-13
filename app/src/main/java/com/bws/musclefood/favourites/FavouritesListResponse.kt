package com.bws.musclefood.favourites

class FavouritesListResponse : ArrayList<FavouritesListResponseItem>()

data class FavouritesListResponseItem(
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