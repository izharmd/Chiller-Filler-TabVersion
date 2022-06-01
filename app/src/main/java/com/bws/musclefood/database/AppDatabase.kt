package com.bws.musclefood.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bws.musclefood.itemcategory.cartlist.CartListResponseItem
import com.bws.musclefood.orders.reorder.OrderItem


@Database(
    entities = [Contact::class, tblBaskets::class,OrderItem::class],
    version = 2,

   /* autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ], exportSchema = true*/
)
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
        )/*.addMigrations(MIGRATION_1_2)*/
            .build()

       /* val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Contact ADD COLUMN age INTEGER")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `GogTest` (`id` INTEGER, `dogOwnerId` LONG,`cuteness` INTEGER,`name` TEXT NOT NULL, " + "PRIMARY KEY(`id`))  ")
            }
        }*/
    }


}
