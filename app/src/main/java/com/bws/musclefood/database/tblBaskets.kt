package com.bws.musclefood.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tblBaskets")
data class tblBaskets(
    val CartItemID: String,
    val FavoriteFlag: String,
    val FormattedPrice: String,
    val FormattedProductTotalPrice: String,
    val Price: String,
    val ProductID: String,
    val ProductImageName: String,
    val ProductName: String,
    val ProductSize: String,
    val Quantity: String

    ) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
