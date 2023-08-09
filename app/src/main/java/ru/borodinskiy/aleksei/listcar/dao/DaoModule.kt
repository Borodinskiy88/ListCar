package ru.borodinskiy.aleksei.listcar.dao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.borodinskiy.aleksei.listcar.db.CarDatabase

@InstallIn(SingletonComponent::class)
@Module
object DaoModule {
    @Provides
    fun provideCarDao(db: CarDatabase): CarDao = db.carDao()
}