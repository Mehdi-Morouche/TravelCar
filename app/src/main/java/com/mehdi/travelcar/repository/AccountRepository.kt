package com.mehdi.travelcar.repository

import androidx.lifecycle.LiveData
import com.mehdi.travelcar.dao.AccountDao
import com.mehdi.travelcar.entities.AccountEntity

/**
 * Created by mehdi on 2020-02-16.
 */
class AccountRepository(private val accountDao: AccountDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val account: LiveData<AccountEntity> = accountDao.getAccount()

    suspend fun insert(account: AccountEntity) {
        accountDao.insert(account)
    }

    suspend fun update(account: AccountEntity) {
        accountDao.update(account)
    }
}