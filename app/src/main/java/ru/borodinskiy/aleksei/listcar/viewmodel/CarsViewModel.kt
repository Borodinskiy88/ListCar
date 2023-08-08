package ru.borodinskiy.aleksei.listcar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.borodinskiy.aleksei.listcar.dao.CarDao
import ru.borodinskiy.aleksei.listcar.entity.Car

class CarsViewModel(private val carDao: CarDao) : ViewModel() {

    val allCars: LiveData<List<Car>> = carDao.getCars().asLiveData()

    /**
     * Обновляет существующий элемент в базе данных.
     */
    fun updateCar(
        id: Int,
        brand: String,
        model: String,
        specifications: String,
        price: String
    ) {
        val updatedCar = getUpdatedCarEntry(id, brand, model, specifications, price)
        updateCar(updatedCar)
    }

    /**
     * Запуск новой сопрограммы для неблокирующего обновления элемента
     */
    private fun updateCar(car: Car) {
        viewModelScope.launch {
            carDao.update(car)
        }
    }

    /**
     * Вставляет новый элемент в базу данных.
     */
    fun addNewCar(brand: String, model: String, specifications: String, price: String) {
        val newCar = getNewCarEntry(brand, model, specifications, price)
        insertCar(newCar)
    }

    /**
     * Запуск новой сопрограммы для неблокирующей вставки элемента
     */
    private fun insertCar(car: Car) {
        viewModelScope.launch {
            carDao.insert(car)
        }
    }

    /**
     * Запуск новой сопрограммы для неблокирующего удаления элемента
     */
    fun deleteCar(car: Car) {
        viewModelScope.launch {
            carDao.delete(car)
        }
    }

    /**
     * Получить элемент из репозитория.
     */
    fun retrieveCar(id: Int): LiveData<Car> {
        return carDao.getCarById(id).asLiveData()
    }

    /**
     * Возвращает true, если EditTexts не пусты
     */
    fun isEntryValid(brand: String, model: String, specifications: String, price: String): Boolean {
        if (brand.isBlank() || model.isBlank() || specifications.isBlank() || price.isBlank()) {
            return false
        }
        return true
    }

    /**
     * Возвращает экземпляр класса сущностей [Item] с информацией об элементе, введенной пользователем.
     * Это будет использоваться для добавления новой записи в базу данных Inventory.
     */
    private fun getNewCarEntry(
        brand: String,
        model: String,
        specifications: String,
        price: String
    ): Car {
        return Car(
            brand = brand,
            model = model,
            specifications = specifications,
            price = price.toInt()
        )
    }

    /**
     * Вызывается для обновления существующей записи в базе данных Inventory.
     * Возвращает экземпляр класса сущностей [Item] с информацией об элементе, обновленной пользователем.
     */
    private fun getUpdatedCarEntry(
        id: Int,
        brand: String,
        model: String,
        specifications: String,
        price: String
    ): Car {
        return Car(
            id = id,
            brand = brand,
            model = model,
            specifications = specifications,
            price = price.toInt()
        )
    }
}

/**
 * Фабричный класс для создания экземпляра [ViewModel].
 */
class CarsViewModelFactory(private val carDao: CarDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarsViewModel(carDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}