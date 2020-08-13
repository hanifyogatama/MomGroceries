package com.binar.momgroceries

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Groceries::class],version = 1)
abstract class GroceriesDatabase : RoomDatabase() {
abstract fun groceriesDao():GroceriesDao
    companion object {
        private var INSTANCE: GroceriesDatabase? = null

        fun getInstance(context: Context): GroceriesDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, GroceriesDatabase::class.java,
                    "groceries_db"
                ).build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}