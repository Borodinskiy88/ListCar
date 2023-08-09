package ru.borodinskiy.aleksei.listcar.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.borodinskiy.aleksei.listcar.dao.CarDao
import ru.borodinskiy.aleksei.listcar.entity.Car

@Database(entities = [Car::class], version = 1)
abstract class CarDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao

}