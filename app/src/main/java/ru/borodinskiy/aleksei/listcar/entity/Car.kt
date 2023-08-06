package ru.borodinskiy.aleksei.listcar.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car")
data class Car(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int,
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "model")
    @NonNull val model: String,
    @ColumnInfo(name = "brand")
    @NonNull val brand: String,
    @ColumnInfo(name = "specifications")
    @NonNull val specifications: String,
    @ColumnInfo(name = "price")
    @NonNull val price: Int
)