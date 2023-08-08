package ru.borodinskiy.aleksei.listcar.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_database")
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "model")
    val model: String,
    @ColumnInfo(name = "brand")
    val brand: String,
    @ColumnInfo(name = "specifications")
    val specifications: String,
    @ColumnInfo(name = "price")
    val price: Int
)