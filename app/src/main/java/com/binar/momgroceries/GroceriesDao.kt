package com.binar.momgroceries

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface GroceriesDao {

    @Insert(onConflict = REPLACE)
    fun addItem(groceries: Groceries): Long

    @Query("SELECT * FROM Groceries")
    fun readAllItem(): List<Groceries>

    @Update
    fun updateItem(groceries: Groceries): Int

    @Delete
    fun deleteItem(groceries: Groceries): Int
}