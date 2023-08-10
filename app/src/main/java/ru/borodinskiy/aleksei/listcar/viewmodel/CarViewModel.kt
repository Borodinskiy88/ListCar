package ru.borodinskiy.aleksei.listcar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.borodinskiy.aleksei.listcar.entity.Car
import ru.borodinskiy.aleksei.listcar.repository.CarRepositoryImpl
import javax.inject.Inject

private val empty = Car(
    id = 0,
    model = "",
    brand = "",
    specifications = "",
    price = 0
)

@HiltViewModel
class CarViewModel @Inject constructor(
    private val repository: CarRepositoryImpl,
) : ViewModel() {

    private val edited = MutableLiveData(empty)

    val allCars: LiveData<List<Car>> = repository.allCars

    fun getCarById(id: Int): LiveData<Car> = repository.getCarById(id)

    fun getCarByBrand(brand: String): LiveData<List<Car>> = repository.getCarByBrand(brand)

    fun getCarByModel(model: String): LiveData<List<Car>> = repository.getCarByModel(model)

    val priceCarDecrease: LiveData<List<Car>> = repository.priceCarDecrease()

    val priceCarIncrease: LiveData<List<Car>> = repository.priceCarIncrease()

    fun update(car: Car) {
        edited.value = car
    }

    fun loadCars(): LiveData<List<Car>> = repository.getCars()


    fun delete(car: Car) {
        viewModelScope.launch {
            repository.delete(car)
        }
    }

    private fun insert(car: Car) {
        viewModelScope.launch {
            repository.insert(car)
        }
    }

    fun save() {
        edited.value?.let { insert(it) }
        clear()
    }

    private fun clear() {
        edited.value = empty
    }

    fun changeCar(brand: String, model: String, specification: String, price: String) {
        edited.value = edited.value?.copy(
            brand = brand, model = model, specifications = specification, price = price.toInt()
        )
    }

    fun searchDatabase(query: String): LiveData<List<Car>> =
        repository.searchDatabase(query)

}
