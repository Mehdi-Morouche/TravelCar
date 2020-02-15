package com.mehdi.travelcar.carset.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by mehdi on 2020-02-15.
 */

@Dao
interface CarSetDao {

    @Query("SELECT * FROM sets WHERE themeId = :themeId ORDER BY year DESC")
    fun getLegoSets(themeId: Int): LiveData<List<CarSet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(legoSets: List<CarSet>)
}
