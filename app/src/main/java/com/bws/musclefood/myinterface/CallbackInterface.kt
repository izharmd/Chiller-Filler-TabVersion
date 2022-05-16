package com.bws.musclefood.myinterface

import com.bws.musclefood.itemcategory.productlist.ProductListResponseItem

interface CallbackInterface {
    fun passResultCallback(message: ProductListResponseItem)
}