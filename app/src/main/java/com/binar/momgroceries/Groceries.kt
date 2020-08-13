package com.binar.momgroceries

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Groceries(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "quantity") var quantity: Int,
    @ColumnInfo(name = "unit") var unit: String,
    @ColumnInfo(name = "unitPrice") var unitPrice: Int,
    @ColumnInfo(name = "isChecked") var isChecked : Boolean
) : Parcelable