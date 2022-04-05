package com.bws.musclefood.login

data class LoginResponse(
    val StatusCode: String,
    val StatusMSG: String,
    val UserId: String,
    val Title: String,
    val FirstName: String,
    val LastName: String,
    val FullName: String,
    val CompanyName: String,
    val VATNo: String,
    val EmailID: String,
    val ProfileImage: String,
    val MobileNo: String
)