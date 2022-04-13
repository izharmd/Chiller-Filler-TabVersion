package com.bws.musclefood.common

import com.bws.musclefood.favourites.FavouritesModel
import com.bws.musclefood.itemcategory.cartlist.CartListModel
import com.bws.musclefood.orders.searchorder.OrderItem

class Constant {

    companion object {

        val BASE_URL = "http://food-hunt.co.uk/ChillerFiller/API/ChillerFillerMobileWS.svc/"

        val APPLE_SIZE_KEY: String = "APPLE_SIZE_KEY"
        val addDataToCart = ArrayList<CartListModel>()
        val arrFavourites = ArrayList<FavouritesModel>()
        var subCategoryData = ArrayList<String>()

        var hashMap = HashMap<String, CartListModel>() //define empty hashmap
        var pos: Int = 0
        var totalCartItem: Int = 0
        var totalFavoritesCartItem: Int = 0
        var clickOnTop: String = "YES"
        var serviceType = "Retail Ready"
        var hidePaymentSection = ""
        var orderNo = ""
        var deliveryAddress = ""
        var paymentDetails = "Payment"
        var retailReady = ""
        var foodService = ""
        var categoryId = ""
        var mainCategory = ""
        var categoryName = ""
        var sessionID = "12345"
        var deviceID = "12345"
        var quantity = "1"
        var searchOrderNo = ""
        var fromDate = ""
        var toDate = ""

        var orderItem = ArrayList<OrderItem>()

    }
}