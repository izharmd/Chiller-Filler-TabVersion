package com.bws.musclefood.common

import com.bws.musclefood.favourites.FavouritesModel
import com.bws.musclefood.itemcategory.cartlist.CartListModel
import com.bws.musclefood.itemcategory.productlist.ProductListResponse
import com.bws.musclefood.itemcategory.productlist.ProductListResponseItem
import com.bws.musclefood.orders.searchorder.OrderItem
import org.json.JSONArray

class Constant {

    companion object {

        val BASE_URL = "http://food-hunt.co.uk/ChillerFiller/API/ChillerFillerMobileWS.svc/"
        val INSERTPLACEORDERDETAILS = "InsertPlaceOrderDetails"

        val addDataToCart = ArrayList<CartListModel>()

        var dataModel = ArrayList<ProductListResponseItem>()



        var hashMap = HashMap<String, CartListModel>() //define empty hashmap
        var pos: Int = 0
        var totalCartItem: Int = 0
        var totalBasketValue = ""
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
        var subCategoryId = "1"
        var cartItem = 0

        var orderItem = ArrayList<OrderItem>()

        val jsonOrder = JSONArray()
        var deliveryDate = ""
        var deliveryTime = ""
        var TotalPrice = 0f
        var row_index = 0

    }
}