package ru.borodinskiy.aleksei.listcar.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CarDatabaseModule {

    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext
        context: Context
    ): CarDatabase = Room.databaseBuilder(context, CarDatabase::class.java, "car_db")
        .createFromAsset("database/car_db.db")
        .build()
}

