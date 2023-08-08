package ru.borodinskiy.aleksei.listcar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import ru.borodinskiy.aleksei.listcar.dao.CarDao
import ru.borodinskiy.aleksei.listcar.entity.Car

class CarRepository(private val carDao: CarDao) {

    val allCars: LiveData<List<Car>> = carDao.getCars().asLiveData()

    fun getCarById(id: Int): LiveData<Car> = carDao.getCarById(id).asLiveData()

    fun getCarByBrand(brand: String): LiveData<List<Car>> = carDao.getCarByBrand(brand).asLiveData()

    fun getCarByModel(model: String): LiveData<List<Car>> = carDao.getCarByModel(model).asLiveData()

    fun priceCarDecrease(): LiveData<List<Car>> = carDao.getCarByPriceDescending().asLiveData()

    fun priceCarIncrease(): LiveData<List<Car>> = carDao.getCarByPriceAscending().asLiveData()

    suspend fun update(car: Car) = carDao.update(car)

    suspend fun insert(car: Car) = carDao.insert(car)

    suspend fun delete(car: Car) = carDao.delete(car)

    suspend fun save(car: Car) = carDao.save(car)
}