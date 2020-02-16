package com.mehdi.travelcar.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Created by mehdi on 2020-02-16.
 */
@Serializable
@Entity(tableName = "equipment")
data class EquipmentEntity(@PrimaryKey @NonNull val id: Int, @ColumnInfo(name = "name") var name: String? = null)