package com.bws.musclefood.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


const val CURRENT_USER_ID1 = 0
@Entity(tableName = "dog")
data class Dog(
   // @PrimaryKey val dogId: Long,
    val dogOwnerId: Long,
    val name: String,
   // val cuteness: Int,
   // val barkVolume: Int,
   // val breed: String
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = CURRENT_USER_ID1
}