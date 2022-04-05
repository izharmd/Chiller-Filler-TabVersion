package com.bws.musclefood.urils

sealed class Resources<T>(
    val data: T? = null,
    val loadingMessage: String? = null,
    val errorMessage: String? = null,
    val noInternetMessage: String? = null
) {

    class Success<T>(data: T? = null) : Resources<T>(data = data)
    class Loading<T>(loadingMessage: String) : Resources<T>(loadingMessage = loadingMessage)
    class Error<T>(errorMessage: String): Resources<T>(errorMessage = errorMessage)
    class NoInternet<T>(noInternetMessage: String):Resources<T>(noInternetMessage = noInternetMessage)
}