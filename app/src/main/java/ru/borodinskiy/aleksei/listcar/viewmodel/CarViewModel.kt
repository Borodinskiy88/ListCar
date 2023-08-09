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

//    private val _created = SingleLiveEvent<Unit>()
//    private val created = MutableLiveData<Unit>
//        get() = _created


    //TODO
    val allCars: LiveData<List<Car>> = repository.allCars

    //TODO конкретная машина в новом окне
    fun getCarById(id: Int): LiveData<Car> = repository.getCarById(id)

    //TODO машины бренда в новом окне
    fun getCarByBrand(brand: String): LiveData<List<Car>> = repository.getCarByBrand(brand)

    //Todo машины марки в новом окне
    fun getCarByModel(model: String): LiveData<List<Car>> = repository.getCarByModel(model)

    //TODO по уменьшению цены
    fun priceCarDecrease(): LiveData<List<Car>> = repository.priceCarDecrease()

    val priceCarDecrease: LiveData<List<Car>> = repository.priceCarDecrease()

    //TODO по увеличению цены
    fun priceCarIncrease(): LiveData<List<Car>> = repository.priceCarIncrease()

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
}
