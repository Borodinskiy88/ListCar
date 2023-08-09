package ru.borodinskiy.aleksei.listcar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import ru.borodinskiy.aleksei.listcar.dao.CarDao
import ru.borodinskiy.aleksei.listcar.entity.Car
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(private val carDao: CarDao) : CarRepository {

    override val allCars: LiveData<List<Car>> = carDao.getCars().asLiveData()

    override fun getCars(): LiveData<List<Car>> = carDao.getCars().asLiveData()

    override fun getCarById(id: Int): LiveData<Car> = carDao.getCarById(id).asLiveData()

    override fun getCarByBrand(brand: String): LiveData<List<Car>> =
        carDao.getCarByBrand(brand).asLiveData()

    override fun getCarByModel(model: String): LiveData<List<Car>> =
        carDao.getCarByModel(model).asLiveData()

    override fun priceCarDecrease(): LiveData<List<Car>> =
        carDao.getCarByPriceDescending().asLiveData()

    override fun priceCarIncrease(): LiveData<List<Car>> =
        carDao.getCarByPriceAscending().asLiveData()

    override suspend fun update(car: Car) = carDao.update(car)

    override suspend fun insert(car: Car) = carDao.insert(car)

    override suspend fun delete(car: Car) = carDao.delete(car)

    override suspend fun save(car: Car) = carDao.save(car)
}