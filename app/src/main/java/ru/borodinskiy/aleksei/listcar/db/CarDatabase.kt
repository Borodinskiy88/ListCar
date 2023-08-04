package ru.borodinskiy.aleksei.listcar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.borodinskiy.aleksei.listcar.dao.CarDao
import ru.borodinskiy.aleksei.listcar.entity.Car

@Database(entities = [Car::class], version = 1)
abstract class CarDatabase : RoomDatabase() {

    abstract fun carDao(): CarDao

    companion object {
        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getDatabase(context: Context): CarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
//                    context.applicationContext,
                    context,
                    CarDatabase::class.java,
//                    "car")
                    "car_database")
                    .createFromAsset("database/car.db")
//                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
//                return instance
                instance
            }
        }
    }
}