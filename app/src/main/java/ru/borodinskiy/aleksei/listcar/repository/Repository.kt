package ru.borodinskiy.aleksei.listcar.repository

import androidx.lifecycle.LiveData
import ru.borodinskiy.aleksei.listcar.entity.Car

interface Repository {
    val allCars: LiveData<List<Car>>

    fun getCars(): LiveData<List<Car>>

    fun getCarById(id: Int): LiveData<Car>

    fun getCarByBrand(brand: String): LiveData<List<Car>>

    fun getCarByModel(model: String): LiveData<List<Car>>

    fun priceCarDecrease(): LiveData<List<Car>>

    fun priceCarIncrease(): LiveData<List<Car>>

    suspend fun update(car: Car)

    suspend fun insert(car: Car)

    suspend fun delete(car: Car)

    suspend fun save(car: Car)
}