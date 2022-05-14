package com.bws.musclefood.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable


const val CURRENT_USER_ID_ = 0

@Entity(tableName = "contact2",indices = [Index(value = ["id"], unique = true)])
data class Contact2(
    val name: String,
    val phone: String

) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
