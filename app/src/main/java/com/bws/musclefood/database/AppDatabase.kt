package com.bws.musclefood.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bws.musclefood.itemcategory.cartlist.CartListResponseItem


@Database(entities = [Contact::class,Contact2::class,Dog::class,Owner::class, tblBaskets::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "contactDB"
        ).build()
    }
}
