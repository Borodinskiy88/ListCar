package ru.borodinskiy.aleksei.listcar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.borodinskiy.aleksei.listcar.dao.CarDao
import ru.borodinskiy.aleksei.listcar.entity.Car

//TODO
private val empty = Car(
    id = 0,
    model = "",
    brand = "",
    specifications = "",
    price = 0
)

class CarViewModel(private val carDao: CarDao) : ViewModel() {
    //TODO
    private val _edited = MutableLiveData(empty)
    val edited: LiveData<Car>
        get() = _edited


    //TODO
    val allCars: LiveData<List<Car>> = carDao.getCars().asLiveData()

    //TODO конкретная машина в новом окне
    fun getCarById(id: Int): LiveData<Car> = carDao.getCarById(id).asLiveData()

    //TODO машины бренда в новом окне
    fun getCarByBrand(brand: String): LiveData<List<Car>> = carDao.getCarByBrand(brand).asLiveData()

    //Todo машины марки в новом окне
    fun getCarByModel(model: String): LiveData<List<Car>> = carDao.getCarByModel(model).asLiveData()

    //TODO по уменьшению цены
    fun priceCarDecrease(): LiveData<List<Car>> = carDao.getCarByPriceDescending().asLiveData()

    val priceCarDecrease: LiveData<List<Car>> = carDao.getCarByPriceDescending().asLiveData()

    //TODO по увеличению цены
    fun priceCarIncrease(): LiveData<List<Car>> = carDao.getCarByPriceAscending().asLiveData()

    val priceCarIncrease: LiveData<List<Car>> = carDao.getCarByPriceAscending().asLiveData()


    private fun insertCar(car: Car) {
        viewModelScope.launch {
            carDao.insert(car)
        }
    }

    //TODO
    fun edit(car: Car) {
        viewModelScope.launch {
            carDao.update(car)
        }
    }

    fun delete(car: Car) {
        viewModelScope.launch {
            carDao.delete(car)
        }
    }


    //TODO
    fun isEntryValid(brand: String, model: String, specifications: String): Boolean {
        if (brand.isBlank() || model.isBlank() || specifications.isBlank()) {
            return false
        }
        return true
    }

    //TODO
    private fun getCars() {
        viewModelScope.launch {
            carDao.getCars()
        }
    }

    private fun getNewCarEntry(
        id: Int,
        brand: String,
        model: String,
        specifications: String,
        price: Int
    ): Car {
        return Car(
            id = id,
            brand = brand,
            model = model,
            specifications = specifications,
            price = price
        )
    }

    fun addNewCar(id: Int, brand: String, model: String, specifications: String, price: Int) {
        val newCar = getNewCarEntry(id, brand, model, specifications, price)
        insertCar(newCar)
    }

}

//TODO
class CarViewModelFactory(private val carDao: CarDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarViewModel(carDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}