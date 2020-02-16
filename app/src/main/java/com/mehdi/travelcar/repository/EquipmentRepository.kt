package com.mehdi.travelcar.repository

import androidx.lifecycle.LiveData
import com.mehdi.travelcar.dao.EquipmentDao
import com.mehdi.travelcar.entities.EquipmentEntity

/**
 * Created by mehdi on 2020-02-16.
 */
class EquipmentRepository(private val equipmentDao: EquipmentDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val equipments: LiveData<List<EquipmentEntity>> = equipmentDao.getEquipments()

    suspend fun insertAll(equipments: List<EquipmentEntity>) {
        equipmentDao.insertAll(equipments)
    }
}