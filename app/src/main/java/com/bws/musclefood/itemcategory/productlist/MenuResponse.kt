package com.bws.musclefood.itemcategory.productlist

class MenuResponse : ArrayList<MenuResponseItem>()


data class MenuResponseItem(
    val Category: String,
    val SubCategory: List<SubCategory>
)

data class SubCategory(
    val CategoryID: String,
    val CategoryName: String
)
