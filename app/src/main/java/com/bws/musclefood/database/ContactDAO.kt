package com.bws.musclefood.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bws.musclefood.User

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

    @Query("SELECT * FROM contact WHERE id > :ids")
    suspend fun getMobileNo(ids: Int): List<Contact>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDog(dog: List<Dog>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun addDogOwner(dogAndOwner: List<Owner>)

  //  @Query("select * from contact where id between :ids and :ids2")
   // suspend fun getIds(ids:Int,ids2:Int) :List<Contact>


    @Transaction
    @Query("SELECT * FROM owner")
    fun getDogsAndOwners(): List<DogAndOwner>
}