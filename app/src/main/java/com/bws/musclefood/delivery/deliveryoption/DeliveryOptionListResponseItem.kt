package com.bws.musclefood.delivery.deliveryoption

data class DeliveryOptionListResponseItem(
    val DefaultAddressFlag: String,
    val DeliveryAddressHouseNumber: String,
    val DeliveryAddressLine1: String,
    val DeliveryAddressLine2: String,
    val DeliveryAddressName: String,
    val DeliveryCity: String,
    val DeliveryContactNumber: String,
    val DeliveryDate: Any,
    val DeliveryPostcode: String,
    val DeliverySlot: String,
    val ID: String,
    val UserID: Any
)