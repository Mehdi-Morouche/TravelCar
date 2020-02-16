package com.mehdi.travelcar.CarEntity.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mehdi.travelcar.entities.CarEntity

/**
 * Created by mehdi on 2020-02-15.
 */

@Dao
interface CarDao {

    @Query("SELECT * FROM sets WHERE themeId = :themeId ORDER BY year DESC")
    fun getLegoSets(themeId: Int): LiveData<List<CarEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(legoSets: List<CarEntity>)
}
