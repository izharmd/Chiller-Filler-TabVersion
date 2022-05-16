package com.bws.musclefood.orders.fragment

import com.bws.musclefood.delivery.choosedeliveryaddress.ChooseDelModel
import com.bws.musclefood.orders.searchorder.OrderItemList

interface CallbackInterface {
    fun passResultCallback(message: String)
    fun viewOrderItems(itemList: ChooseDelModel)
}