package com.mehdi.travelcar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mehdi.travelcar.dao.EquipmentDao
import com.mehdi.travelcar.entities.EquipmentEntity

/**
 * Created by mehdi on 2020-02-16.
 */
@Database(entities = arrayOf(EquipmentEntity::class), version = 1, exportSchema = false)
abstract class EquipmentDatabase : RoomDatabase() {

    abstract fun equipmentDao(): EquipmentDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: EquipmentDatabase? = null

        fun getDatabase(context: Context): EquipmentDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EquipmentDatabase::class.java,
                    "equipment_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}