package com.mehdi.travelcar.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mehdi.travelcar.entities.EquipmentEntity

/**
 * Created by mehdi on 2020-02-16.
 */
@Dao
interface EquipmentDao {

    @Query("SELECT * from equipment")
    fun getEquipments(): LiveData<List<EquipmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(equipment: List<EquipmentEntity>)
}