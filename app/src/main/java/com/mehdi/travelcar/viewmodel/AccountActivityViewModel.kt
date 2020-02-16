package com.mehdi.travelcar.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.mehdi.travelcar.db.AccountDatabase
import com.mehdi.travelcar.entities.AccountEntity
import com.mehdi.travelcar.repository.AccountRepository
import kotlinx.coroutines.launch

/**
 * Created by mehdi on 2020-02-16.
 */

class AccountActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AccountRepository

    val account: LiveData<AccountEntity>

    init {
        val accountDao = AccountDatabase.getDatabase(application).accountDao()
        repository = AccountRepository(accountDao)
        account = repository.account
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(account: AccountEntity) = viewModelScope.launch {
        repository.insert(account)
    }

    fun update(account: AccountEntity) = viewModelScope.launch {
        repository.update(account)
    }
}