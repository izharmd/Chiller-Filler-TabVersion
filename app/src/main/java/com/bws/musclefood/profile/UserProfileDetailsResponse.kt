package com.bws.musclefood.profile

data class UserProfileDetailsResponse(
    val CompanyName: String,
    val EmailID: String,
    val FirstName: String,
    val LastName: String,
    val MobilePhone: String,
    val ProfileImage: String,
    val StatusCode: String,
    val StatusMSG: String,
    val Title: String,
    val UserID: String,
    val VATNo: String
)