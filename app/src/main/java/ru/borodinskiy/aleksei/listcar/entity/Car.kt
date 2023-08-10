package ru.borodinskiy.aleksei.listcar.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_db")
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "model")
    val model: String,
    @ColumnInfo(name = "brand")
    val brand: String,
    @ColumnInfo(name = "specifications")
    val specifications: String,
    @ColumnInfo(name = "price")
    val price: Int
)