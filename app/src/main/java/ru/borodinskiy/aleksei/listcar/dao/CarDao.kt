package ru.borodinskiy.aleksei.listcar.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.borodinskiy.aleksei.listcar.entity.Car

@Dao
interface CarDao {
    //Todo
    @Query("SELECT * FROM car ORDER BY id ASC")
    fun getCars(): Flow<List<Car>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(car: Car)

    @Update
    suspend fun update(car: Car)

    @Delete
    suspend fun delete(car: Car)

    @Query("SELECT * from car WHERE id = :id")
    fun getCarById(id: Int): Flow<Car>

    @Query("SELECT * from car WHERE brand = :brand")
    fun getCarByBrand(brand: String): Flow<List<Car>>

    @Query("SELECT * from car WHERE model = :model")
    fun getCarByModel(model: String): Flow<List<Car>>

    @Query("SELECT * from car ORDER BY price DESC")
    fun getCarByPriceDescending(): Flow<List<Car>>

    @Query("SELECT * from car ORDER BY price ASC")
    fun getCarByPriceAscending(): Flow<List<Car>>
}