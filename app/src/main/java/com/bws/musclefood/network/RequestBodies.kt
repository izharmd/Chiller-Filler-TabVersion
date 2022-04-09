package com.bws.musclefood.network

object RequestBodies {


    data class LoginBody(
        val EmailID: String,
        val Password: String,
        val DeviceType: String,
        val DeviceID: String
    )

    data class PopulateProductDetailsBody(
        val UserID: String,
        val SubCategoryID: String,
        val SubCategoryName: String,
        val Key: String,
        val Category: String
    )


    data class GetAllCartDetailsBody(val UserID: String, val SessionID: String)


    data class PopulateProductDetailsByProductIDBody(
        val UserID: String,
        val Category: String,
        val SubCategoryID: String,
        val SubCategoryName: String,
        val Keya: String,
    )

    class GetMenu {}


    class InsertUpdateCart : ArrayList<InsertUpdateCartItem>()

    data class InsertUpdateCartItem(
        val CartItemID: String,
        val Price: String,
        val ProductID: String,
        val Quantity: String,
        val SessionID: String,
        val TotalPrice: String,
        val UserID: String
    )

}