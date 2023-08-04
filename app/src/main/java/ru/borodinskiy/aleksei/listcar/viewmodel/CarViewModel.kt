package ru.borodinskiy.aleksei.listcar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.borodinskiy.aleksei.listcar.dao.CarDao
import ru.borodinskiy.aleksei.listcar.entity.Car

class CarViewModel (private val carDao: CarDao) : ViewModel() {

    private fun insertCar(car : Car) {
        viewModelScope.launch {
            carDao.insert(car)
        }
    }

    //TODO
    private fun getCars() {
        viewModelScope.launch {
            carDao.getCars()
        }
    }

    private fun getNewCarEntry(id: Int, brand: String, model: String, specifications: String, price: Int): Car {
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