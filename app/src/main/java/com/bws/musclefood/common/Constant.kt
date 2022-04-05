package com.bws.musclefood.common

import com.bws.musclefood.favourites.FavouritesModel
import com.bws.musclefood.itemcategory.cartlist.CartListModel

class Constant {

    companion object {
        val APPLE_SIZE_KEY: String = "APPLE_SIZE_KEY"
        val addDataToCart = ArrayList<CartListModel>()
        val arrFavourites = ArrayList<FavouritesModel>()
        var subCategoryData = ArrayList<String>()
        var pos:Int = 0
        var totalCartItem:Int = 0
        var totalFavoritesCartItem:Int = 0
        var clickOnTop:String = "YES"
        var serviceType = "Retail Ready"
        var hidePaymentSection = ""
        var orderNo = ""
        var deliveryAddress = ""
        var paymentDetails = "Payment"
        var categoryId = ""
        var categoryName = ""
    }
}