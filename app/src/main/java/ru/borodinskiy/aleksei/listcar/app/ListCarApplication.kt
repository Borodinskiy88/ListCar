package ru.borodinskiy.aleksei.listcar.app

import android.app.Application
import ru.borodinskiy.aleksei.listcar.db.CarDatabase

class ListCarApplication : Application() {

    val database: CarDatabase by lazy { CarDatabase.getDatabase(this) }

}