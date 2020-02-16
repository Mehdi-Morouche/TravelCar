package com.mehdi.travelcar.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mehdi.travelcar.entities.CarEntity

/**
 * Created by mehdi on 2020-02-16.
 */
@Dao
interface CarDao {

    @Query("SELECT * from car")
    fun getCars(): LiveData<List<CarEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cars: List<CarEntity>)
}