package com.mehdi.travelcar.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mehdi.travelcar.entities.AccountEntity

/**
 * Created by mehdi on 2020-02-16.
 */
@Dao
interface AccountDao {

    @Query("SELECT * from account")
    fun getAccount(): LiveData<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountEntity)

    @Update
    suspend fun update(vararg account: AccountEntity)

    /*@Query("DELETE FROM account")
    suspend fun deleteAll()*/
}