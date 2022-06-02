package com.bws.musclefood.orders.reorder

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "OrderItem")
data class OrderItem(
    val CateGories: String,
    val FavoriteFlag: String,
    val ItemPrice: String,
    val ItemQty: String,
    val OrderID: String,
    val OrderItem: String,
    val ProductID: String,
    val ProductVersion: String,
    val SubCateGories: String,
    val ItemRate: String,
    val ItemImage: String
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
