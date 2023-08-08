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
import ru.borodinskiy.aleksei.listcar.utils.SingleLiveEvent

//TODO
private val empty = Car(
    id = 0,
    model = "",
    brand = "",
    specifications = "",
    price = 0
)

class CarViewModel(private val carDao: CarDao) : ViewModel() {

    // TODO
//    private val _edited = MutableLiveData(empty)
//    val edited: LiveData<Car>
//        get() = _edited

    private val edited = MutableLiveData(empty)

    private val _created = SingleLiveEvent<Unit>()
    val created: LiveData<Unit>
        get() = _created


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


//    suspend fun insert(car: Car) {
//        viewModelScope.launch {
//            carDao.insert(car)
//        }
//    }

    //TODO
//    fun update(car: Car) {
//        viewModelScope.launch {
//            carDao.update(car)
//        }
//    }

    fun update(car: Car) {
        edited.value = car
    }

    fun loadCars() {
        viewModelScope.launch {
            carDao.getCars()
        }
    }

    fun delete(car: Car) {
        viewModelScope.launch {
            carDao.delete(car)
        }
    }

    fun insert() {
        edited.value?.let {
            viewModelScope.launch {
                //                              carDao.save(it)
                carDao.insert(it)
            }
        }
    }

    fun changeCar(brand: String, model: String, specification: String, price: String) {
        edited.value = edited.value?.copy(
            brand = brand, model = model, specifications = specification, price = price.toInt()
        )
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