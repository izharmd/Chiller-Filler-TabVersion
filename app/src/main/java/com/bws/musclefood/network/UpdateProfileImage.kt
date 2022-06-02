package com.bws.musclefood.network

data class UpdateProfileImage(
    val ImageByte: List<Int>,
    val ImageStream: ImageStream,
    val UserID: String
)