package com.mehdi.travelcar.repository

import androidx.lifecycle.LiveData
import com.mehdi.travelcar.dao.CarDao
import com.mehdi.travelcar.entities.CarEntity

/**
 * Created by mehdi on 2020-02-16.
 */
class CarRepository(private val carDao: CarDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val cars: LiveData<List<CarEntity>> = carDao.getCars()

    suspend fun insertAll(car: List<CarEntity>) {
        carDao.insertAll(car)
    }
}