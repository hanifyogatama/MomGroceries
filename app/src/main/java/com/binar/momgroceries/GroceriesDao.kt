package com.binar.momgroceries

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface GroceriesDao {

    @Insert(onConflict = REPLACE)
    fun addItem(item: Groceries): Long

    @Query("SELECT * FROM Groceries")
    fun readAllItem(): List<Groceries>

    @Update
    fun updateItem(item: Groceries): Int

    @Delete
    fun deleteItem(item: Groceries): Int
}