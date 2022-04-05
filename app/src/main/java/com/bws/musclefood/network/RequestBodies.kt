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
        val Key: String
    )

    data class GetAllCartDetailsBody(val UserID: String, val SessionID: String)

     class GetMenu{  }


}