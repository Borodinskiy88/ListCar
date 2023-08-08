package ru.borodinskiy.aleksei.listcar.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.borodinskiy.aleksei.listcar.entity.Car

@Dao
interface CarDao {
    @Query("SELECT * FROM car_database ORDER BY id DESC")
    fun getCars(): Flow<List<Car>>

    @Query("SELECT * from car_database WHERE id = :id")
    fun getCarById(id: Int): Flow<Car>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(car: Car)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cars: List<Car>)

    @Update
    suspend fun update(car: Car)

    @Delete
    suspend fun delete(car: Car)

    @Upsert
    suspend fun save(car: Car)

    //Сортировка

    @Query("SELECT * from car_database WHERE brand = :brand")
    fun getCarByBrand(brand: String): Flow<List<Car>>

    @Query("SELECT * from car_database WHERE model = :model")
    fun getCarByModel(model: String): Flow<List<Car>>

    @Query("SELECT * from car_database ORDER BY price DESC")
    fun getCarByPriceDescending(): Flow<List<Car>>

    @Query("SELECT * from car_database ORDER BY price ASC")
    fun getCarByPriceAscending(): Flow<List<Car>>

}