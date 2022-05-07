package com.bws.musclefood.interfaceCallback

import com.bws.musclefood.itemcategory.productlist.ProductListResponseItem

interface CallbackInterface {
    fun passResultCallback(message: ProductListResponseItem)
}