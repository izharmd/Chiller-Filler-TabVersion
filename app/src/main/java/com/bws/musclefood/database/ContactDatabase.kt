package com.bws.musclefood.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Contact::class,Contact2::class,Dog::class,Owner::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDAO

    companion object {

        @Volatile
        private var _INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {

            if (_INSTANCE == null) {
                synchronized(this) {
                    _INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "contactDB"
                    ).fallbackToDestructiveMigration()

                        .build()
                }
            }

            return _INSTANCE!!
        }

    }
}