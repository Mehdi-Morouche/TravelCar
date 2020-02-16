package com.mehdi.travelcar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mehdi.travelcar.dao.AccountDao
import com.mehdi.travelcar.entities.AccountEntity

/**
 * Created by mehdi on 2020-02-16.
 */
@Database(entities = arrayOf(AccountEntity::class), version = 1, exportSchema = false)
abstract class AccountDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AccountDatabase? = null

        fun getDatabase(context: Context): AccountDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccountDatabase::class.java,
                    "account_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}