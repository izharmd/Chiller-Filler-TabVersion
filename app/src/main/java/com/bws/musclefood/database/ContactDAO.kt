package com.bws.musclefood.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bws.musclefood.User
import com.bws.musclefood.itemcategory.cartlist.CartListResponse
import com.bws.musclefood.itemcategory.cartlist.CartListResponseItem
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


    @Query("SELECT * FROM contact WHERE id > :ids")
    suspend fun getMobileNo(ids: Int): List<Contact>


    @Query("SELECT * FROM tblBaskets")
    suspend fun getProductItem(): List<tblBaskets>


    @Query("INSERT INTO contact2  SELECT * FROM  contact")
    suspend fun getMobileNo1()


    @Query("SELECT * FROM contact2")
    fun getContactData(): LiveData<List<Contact2>>


    // @Insert(onConflict = OnConflictStrategy.REPLACE)
    // suspend fun addDog(dog: List<Dog>)


    //  @Insert(onConflict = OnConflictStrategy.REPLACE)
    // suspend fun addDogOwner(dogAndOwner: List<Owner>)

    //  @Query("select * from contact where id between :ids and :ids2")
    // suspend fun getIds(ids:Int,ids2:Int) :List<Contact>


    @Transaction
    @Query("SELECT * FROM owner")
    fun getDogsAndOwners(): List<DogAndOwner>
}