package com.mehdi.travelcar.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mehdi.travelcar.entities.CarWithEquipments

/**
 * Created by mehdi on 2020-02-16.
 */
@Dao
interface CarEquipmentDao {
    @Query("SELECT * FROM equipment")
    fun getCarWithEquipment(): LiveData<List<CarWithEquipments>>

}