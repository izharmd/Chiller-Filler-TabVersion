package com.bws.musclefood.database

import android.media.session.MediaSession
import androidx.lifecycle.LiveData
import androidx.room.*
import com.bws.musclefood.User
import com.bws.musclefood.itemcategory.cartlist.CartListResponse
import com.bws.musclefood.itemcategory.cartlist.CartListResponseItem
import com.bws.musclefood.orders.reorder.OrderItem
import com.bws.musclefood.utils.Resources

@Dao
interface ContactDAO {

    @Insert
    suspend fun insertContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contact")
    fun getContact(): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDistrict(districtList: List<Contact>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProductItems(productList: List<tblBaskets>)

    @Query("DELETE FROM tblBaskets")
    suspend fun deleteProductItems()

    @Query("UPDATE  tblBaskets SET Quantity = :qty WHERE ProductID = :productID")
    suspend fun updateProductItems(qty: String, productID: String)


    @Query("SELECT * FROM tblBaskets")
    suspend fun getProductItem(): List<tblBaskets>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReorderItems(reorderList: List<OrderItem>)

    @Query("DELETE FROM OrderItem")
    suspend fun deleteReorderItems()

    @Query("UPDATE  OrderItem SET ItemQty = :qty, ItemPrice= :price  WHERE ProductID = :productID")
    suspend fun updateReorderItems(qty: String,price:String, productID: String)

    @Query("SELECT * FROM OrderItem")
    suspend fun getReorderItems():List<OrderItem>

    @Query("SELECT * FROM OrderItem where ProductID=:productId")
    suspend fun getReorderPrice(productId:String):List<OrderItem>




}